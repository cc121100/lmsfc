package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.task.exception.GenerateArtEleException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomchen on 15-3-18.
 */
@Component
public class FilterService {

    private Logger logger = Logger.getLogger(FilterService.class);

    private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

    public ArticleTaskJob filter(ArticleTaskJob atj){

        String preUrl = ""; // http://jinnianshilongnian.iteye.com
        String url = atj.getUrl(); // http://jinnianshilongnian.iteye.com/blog/2018936/
        String[] urls = url.split("//");

        preUrl = urls[0] + "//" + urls[1].substring(0,urls[1].indexOf("/"));

        try{
            //1 add domian for all css which is related path
            String outerCssStr = (String) atj.getTempMap().get("outercss");
            if(StringUtils.isNotEmpty(outerCssStr)){
//                String str = "href=\"" + atj.getFilterRule().getSourceDomain() + "/";
                String str = "href=\"" + preUrl + "/";
                String formatStr = outerCssStr.replaceAll("href=\"/",str);

                atj.getTempMap().put("outercss",formatStr);
            }

            // 2 add domain for all images/href in content which is related path
            String contentStr = (String) atj.getTempMap().get("content");
            if(StringUtils.isNotEmpty(contentStr)){
                String srcStr  = "src=\"" + preUrl + "/";
                String hrefStr  = "href=\"" + preUrl + "/";
                String formatStr = contentStr.replaceAll("src=\"/", srcStr).replaceAll("href=\"/",hrefStr);
                atj.getTempMap().put("content",formatStr);
//            class="img-responsive"
            }

            //3 remove all <!-- --> in content

            // if oschina , filter title
            if(atj.getFilterRule().getName().startsWith("oschina")){
                String regEx_html="<span.*</span>";
                String titleStr = (String) atj.getTempMap().get("title");
                Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
                Matcher m_html=p_html.matcher(titleStr);
                titleStr = m_html.replaceAll("");
                atj.getTempMap().put("title",titleStr);
            }

        }catch (Exception e){
            logger.error("Error occurs when filte atj.", e);
            throw new GenerateArtEleException(e,atj);
        }

        return atj;
    }

    public static void main(String[] args){
//        String str = "http://jinnianshilongnian.iteye.com/blog/2018936/";
//        String[] strs = str.split("//");
//        String result = strs[0] + "//" + strs[1].substring(0,strs[1].indexOf("/"));
//        System.out.println(result);


//        if(str.endsWith("/")){
//            str = str.substring(0,str.length()-1);
//            System.out.println(str);
//        }
//        System.out.println(str.substring(0, str.lastIndexOf("/")));

        String htmlStr = "<span class=\"icon\" style=\"background:#44ac57;\"  title=\"原创博客\">原</span>\t\t\t<span class=\"icon\" style=\"background:#fd9c47;\" title=\"首页推荐过的博客\">荐</span>\t\t\t<span class=\"icon\" style=\"background:#fd6245;\"  title=\"个人博客列表置顶博客\">顶</span>\t\t\t\t\t\t\t\t\t分布式事务系列（1.2）Spring的事务体系";
        String regEx_html="<span.*</span>"; //定义HTML标签的正则表达式
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签
        System.err.println(htmlStr.trim());
    }


}
