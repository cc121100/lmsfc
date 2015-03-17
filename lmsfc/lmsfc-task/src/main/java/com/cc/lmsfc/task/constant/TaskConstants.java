package com.cc.lmsfc.task.constant;

import com.cc.lmsfc.common.util.SpringPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * Created by tomchen on 15-3-17.
 */
@Component
public class TaskConstants {

    public static String TEMP_FLODER;
    public static String NAME;

//    @Autowired
//    public  TaskConstants(@Value("${TEMP_FLOGER}") String tempFloder,
//                          @Value("${jdbc.username}") String userName){
//        this.TEMP_FLODER = tempFloder;
//        this.NAME = userName;
//    }

    {
        TEMP_FLODER = SpringPropertiesUtil.getProperty("TEMP_FLODER");
        NAME = SpringPropertiesUtil.getProperty("jdbc.username");
    }
}
