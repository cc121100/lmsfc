package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.task.exception.GenerateArtEleException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by tomchen on 15-3-18.
 */
@Component
public class FilterService {

    private Logger logger = Logger.getLogger(FilterService.class);

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

        }catch (Exception e){
            logger.error("Error occurs when filte atj.", e);
            throw new GenerateArtEleException(e,atj);
        }

        return atj;
    }

    public static void main(String[] args){
        String str = "http://jinnianshilongnian.iteye.com/blog/2018936/";
        String[] strs = str.split("//");
        String result = strs[0] + "//" + strs[1].substring(0,strs[1].indexOf("/"));
        System.out.println(result);


//        if(str.endsWith("/")){
//            str = str.substring(0,str.length()-1);
//            System.out.println(str);
//        }
//        System.out.println(str.substring(0, str.lastIndexOf("/")));
    }


}
