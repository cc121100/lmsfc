package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import freemarker.core.Configurable;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomchen on 15-3-19.
 */
@Component
public class ArticleService {

    @Autowired
    private Configuration configuration;

    public ArticleTaskJob generate(ArticleTaskJob atj){

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("title",atj.getTempMap().get("title"));
            map.put("content",atj.getTempMap().get("content"));
            map.put("innnercss",atj.getTempMap().get("innnercss"));
            map.put("outercss",atj.getTempMap().get("outercss"));
            map.put("from",atj.getUrl());

            Template template = configuration.getTemplate("template.ftl");

            Writer out = new OutputStreamWriter(new FileOutputStream(new File(atj.getArticleElement().getFileLocation() + CommonConsts.SLASH + "result.html")),"UTF-8");
            template.process(map,out);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return atj;
    }

}
