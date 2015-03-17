package com.cc.lmsfc.common.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-3-5.
 */
public class HttpClientUtil {

    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 定义编码格式 UTF-8
     */
    public static final String ENCODE_UTF8 = "UTF-8";

    /**
     * 定义编码格式 GBK
     */
    public static final String ENCODE_GBK = "GBK";

    private static PoolingHttpClientConnectionManager connManager = null;

    private static CloseableHttpClient httpclient = null;

    private static RequestConfig postReqCfg = null;

    private static RequestConfig getReqCfg = null;

    private static final String URL_PARAM_CONNECT_FLAG = "&";

    private static final String EMPTY = "";

    private static int CONTIMEOUT = 7000;

    private static int SOTIMEOUT = 7000;

    private static int maxConnectionPerHost = 20;

    private static int maxTotalConnections = 20;

    static {
        try {


            SSLContext sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, new TrustStrategy() {

                        @Override
                        public boolean isTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
                            return true;
                        }
                    }).build();

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();

            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            httpclient = HttpClients.custom().setConnectionManager(connManager).build();
            // Create socket configuration
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            connManager.setDefaultSocketConfig(socketConfig);
            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(200)
                    .setMaxLineLength(2000)
                    .build();
            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints)
                    .build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(20);

            postReqCfg = RequestConfig.custom()
                    .setSocketTimeout(SOTIMEOUT)
                    .setConnectTimeout(CONTIMEOUT)
                    .setConnectionRequestTimeout(CONTIMEOUT)
                    .setExpectContinueEnabled(false).build();

            getReqCfg = RequestConfig.custom()
                    .setSocketTimeout(SOTIMEOUT)
                    .setConnectTimeout(CONTIMEOUT)
                    .setConnectionRequestTimeout(CONTIMEOUT).build();

        }
//        catch (KeyManagementException e) {
//            logger.error("KeyManagementException", e);
//        } catch (NoSuchAlgorithmException e) {
//            logger.error("NoSuchAlgorithmException", e);
//        }
        catch (Exception e){
            logger.error("Exception",e);
        }
    }

    public static String postJsonBody(String url, int timeout, Map<String, Object> map, String encoding) throws IOException {
        HttpPost post = new HttpPost(url);
        try {
            post.setHeader("Content-type", "application/json");
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(timeout)
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setExpectContinueEnabled(false).build();
            post.setConfig(requestConfig);

//            String str1 = JsonUtil.objectToJson(map).replace("\\", "");
            String str1 = JacksonUtil.toJSon(map);
            post.setEntity(new StringEntity(str1, encoding));
            logger.info("[HttpUtils Post] begin invoke url:" + url + " , params:"+str1);
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if(entity != null){
                        String str = EntityUtils.toString(entity, encoding);
                        logger.info("[HttpUtils Post]Debug response, url :" + url + " , response string :"+str);
                        return str;
                    }
                } finally {
                    if(entity != null){
                        entity.getContent().close();
                    }
                }
            } finally {
                if(response != null){
                    response.close();
                }
            }
        } finally {
            post.releaseConnection();
        }
        return "";
    }


    public static byte[] httpGet(String url) throws IOException {
        return invokeGet(url, null, ENCODE_UTF8, CONTIMEOUT,SOTIMEOUT);
    }

    private static byte[] invokeGet(String url, Map<String,String> params, String encode, int conTimeout, int soTimeout) throws IOException {
        byte[] responseBytes = null;
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        int i = 0;
        if(params !=null ){
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (i == 0 && !url.contains("?")) {
                    sb.append("?");
                } else {
                    sb.append(URL_PARAM_CONNECT_FLAG);
                }
                sb.append(entry.getKey());
                sb.append("=");
                String value = entry.getValue();
                try {
                    sb.append(URLEncoder.encode(value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.warn("encode http get params error, value is "+value, e);
                    sb.append(URLEncoder.encode(value));
                }
                i++;
            }
        }

        logger.info("[HttpUtils Get] begin invoke:" + sb.toString());
        HttpGet get = new HttpGet(sb.toString());
        get.setConfig(getReqCfg);

        try {
            CloseableHttpResponse response = httpclient.execute(get);
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if(entity != null){
                        responseBytes = EntityUtils.toByteArray(entity);
                    }
                } finally {
                    if(entity != null){
                        entity.getContent().close();
                    }
                }
            }finally {
                if(response != null){
                    response.close();
                }
            }
            logger.info(String.format("[HttpUtils Get]Debug url:%s , response string %s:",url, sb.toString()));
        } finally {
            get.releaseConnection();
        }
        return responseBytes;

    }

//    @SuppressWarnings("deprecation")
//    public static String invokeGet(String url, Map<String, String> params, String encode, int connectTimeout,
//                                   int soTimeout) {
//        String responseString = null;
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setSocketTimeout(soTimeout)
//                .setConnectTimeout(connectTimeout)
//                .setConnectionRequestTimeout(connectTimeout).build();
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(url);
//        int i = 0;
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            if (i == 0 && !url.contains("?")) {
//                sb.append("?");
//            } else {
//                sb.append("&");
//            }
//            sb.append(entry.getKey());
//            sb.append("=");
//            String value = entry.getValue();
//            try {
//                sb.append(URLEncoder.encode(value, "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                logger.warn("encode http get params error, value is "+value, e);
//                sb.append(URLEncoder.encode(value));
//            }
//            i++;
//        }
//        logger.info("[HttpUtils Get] begin invoke:" + sb.toString());
//        HttpGet get = new HttpGet(sb.toString());
//        get.setConfig(requestConfig);
//
//        try {
//            CloseableHttpResponse response = httpclient.execute(get);
//            try {
//                HttpEntity entity = response.getEntity();
//                try {
//                    if(entity != null){
//                        responseString = EntityUtils.toString(entity, encode);
//                    }
//                } finally {
//                    if(entity != null){
//                        entity.getContent().close();
//                    }
//                }
//            } catch (Exception e) {
//                logger.error(String.format("[HttpUtils Get]get response error, url:%s", sb.toString()), e);
//                return responseString;
//            } finally {
//                if(response != null){
//                    response.close();
//                }
//            }
//            logger.info(String.format("[HttpUtils Get]Debug url:%s , response string %s:", sb.toString(), responseString));
//        } catch (SocketTimeoutException e) {
//            logger.error(String.format("[HttpUtils Get]invoke get timout error, url:%s", sb.toString()), e);
//            return responseString;
//        } catch (Exception e) {
//            logger.error(String.format("[HttpUtils Get]invoke get error, url:%s", sb.toString()), e);
//        } finally {
//            get.releaseConnection();
//        }
//        return responseString;
//    }

//    public final static int connectTimeout = 5000;
//    /**
//     * HTTPS请求，默认超时为5S
//     * @param reqURL
//     * @param params
//     * @return
//     */
//    public static String connectPostHttps(String reqURL, Map<String, String> params) {
//
//        String responseContent = null;
//
//        HttpPost httpPost = new HttpPost(reqURL);
//        try {
//            RequestConfig requestConfig = RequestConfig.custom()
//                    .setSocketTimeout(connectTimeout)
//                    .setConnectTimeout(connectTimeout)
//                    .setConnectionRequestTimeout(connectTimeout).build();
//
//            List<NameValuePair> formParams = new ArrayList<>();
//            httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
//            httpPost.setConfig(requestConfig);
//            // 绑定到请求 Entry
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//            CloseableHttpResponse response = httpclient.execute(httpPost);
//            try {
//                // 执行POST请求
//                HttpEntity entity = response.getEntity(); // 获取响应实体
//                try {
//                    if (null != entity) {
//                        responseContent = EntityUtils.toString(entity, Consts.UTF_8);
//                    }
//                } finally {
//                    if(entity != null){
//                        entity.getContent().close();
//                    }
//                }
//            } finally {
//                if(response != null){
//                    response.close();
//                }
//            }
//            logger.info("requestURI : "+httpPost.getURI()+", responseContent: " + responseContent);
//        } catch (ClientProtocolException e) {
//            logger.error("ClientProtocolException", e);
//        } catch (IOException e) {
//            logger.error("IOException", e);
//        } finally {
//            httpPost.releaseConnection();
//        }
//        return responseContent;
//
//    }

}
