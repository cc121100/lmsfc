package com.cc.lmsfc.task.helper;

import com.cc.lmsfc.common.constant.CommonConsts;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;

/**
 * Created by tomchen on 15-4-23.
 */
@Component
public class FreemarkerHelper {

    @Autowired
    private Configuration configuration;

    public void assemble(Map<String,Object> paramMap, String templatePath,String destFileStr) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templatePath);

        File destFile = new File(destFileStr);
        Writer out = new OutputStreamWriter(new FileOutputStream(destFile), CommonConsts.UTF8);
        template.process(paramMap,out);
        out.flush();

    }
}
