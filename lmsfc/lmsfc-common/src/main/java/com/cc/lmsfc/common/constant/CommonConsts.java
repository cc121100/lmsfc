package com.cc.lmsfc.common.constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tomchen on 15-3-18.
 */
public class CommonConsts {

    public final static String SLASH = "/";

    public final static String UTF8 = "UTF-8";
    public final static String SINGLE_TSK = "S";
    public final static String BATCH_TSK = "B";

    public static Map<String,String> artStateMap = null;

    static {
        artStateMap = new LinkedHashMap<>();
        artStateMap.put("0","New");
        artStateMap.put("100","Finished");
        artStateMap.put("110","Pre Get");
        artStateMap.put("111","Ing Get");
        artStateMap.put("112","Fail Get");
        artStateMap.put("120","Pre Generate");
        artStateMap.put("121","Ing Generate");
        artStateMap.put("122","Fail Generate");
        artStateMap.put("130","Pre Deploy");
        artStateMap.put("131","Ing Deploy");
        artStateMap.put("132","Fail Deploy");
    }

    public static int updateArtState(int currentState,boolean isSuccess){
        String currentStateStr = currentState+"";
        int newArtState = 0;

        if(currentStateStr.endsWith("0")){
            newArtState = currentState + 1;
        }else if (currentStateStr.endsWith("2")){
            newArtState = currentState - 1;
        }else if (currentStateStr.endsWith("1")){
            if(isSuccess){
                newArtState = currentState + 9;
            }else {
                newArtState = currentState + 1;
            }
        }

        return newArtState;
    }

}
