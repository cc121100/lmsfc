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

    static {
        PropertyUtil.loadProperties("task_mac.properties");
        ART_ELE_FLODER =PropertyUtil.getValue("ART_ELE_FLODER");
    }
}
