package com.cc.lmsfc.task.constant;

import com.cc.lmsfc.common.util.PropertyUtil;
import com.cc.lmsfc.common.util.SpringPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * Created by tomchen on 15-3-17.
 */
public class TaskConstants {

    public static String ART_ELE_FLODER;
    public static String GENERATED_FLODER;
    public static String DEPLOY_FLODER;
    public static String REASSEMBLE_LOG;

    public static String DEPLOY_INIT_CONTENT_NAME = "deploy_init_content";


    static {
//        PropertyUtil.loadProperties("task.properties");
        PropertyUtil.loadProperties("task.properties");
        ART_ELE_FLODER =PropertyUtil.getValue("ART_ELE_FLODER");
        GENERATED_FLODER = PropertyUtil.getValue("GENERATED_FLODER");
        DEPLOY_FLODER = PropertyUtil.getValue("DEPLOY_FLODER");
        REASSEMBLE_LOG = PropertyUtil.getValue("REASSEMBLE_LOG");
    }


}
