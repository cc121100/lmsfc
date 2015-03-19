package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by tomchen on 15-3-18.
 */
@Component
public class FilterService {


    public ArticleTaskJob filter(ArticleTaskJob atj){

        //1 add domian for all css which is related path
        String outerCssStr = (String) atj.getTempMap().get("outercss");
        if(StringUtils.isNotEmpty(outerCssStr)){
            String str = "href=\"" + atj.getFilterRule().getSourceDomain() + "/";
            String formatStr = outerCssStr.replaceAll("href=\"/",str);

            atj.getTempMap().put("outercss",formatStr);
        }

        // 2 add domain for all images in content which is related path
        String contentStr = (String) atj.getTempMap().get("content");
        if(StringUtils.isNotEmpty(contentStr)){
            String str  = "src=\"" + atj.getFilterRule().getSourceDomain() + "/";
            String formatStr = contentStr.replaceAll("src=\"", str).replaceAll("","");
//            class="img-responsive"
        }

        //2 remove all <!-- --> in content



        return atj;
    }


}
