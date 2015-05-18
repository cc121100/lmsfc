package com.cc.lmsfc.common.constant;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tomchen on 15-3-18.
 */
public class CommonConsts {

    public final static String SLASH = File.separator;

    public final static String UTF8 = "UTF-8";
    public final static String SINGLE_TSK = "S";
    public final static String BATCH_TSK = "B";
    public final static String REASSEMBLE_TSK = "RA";
    public final static String INIT_PAGE_REDIS_TSK = "IPR";
    public final static String INIT_REDIS_TSK = "IR";


    public final static String ELE_PREFIX = "Ele_of_";

    public final static String REG_HTML_TAG= "</?[^>]+>";
    public final static String REG_HTML_CHAR= "<a>\\\\s*|\\t|\\r|\\n</a>";

    //redis

    public final static String REDIS_ART_ZSET_PRE = "art.zset.";
    public final static String REDIS_ART_HASH_PRE = "art.hash.";
    public final static String REDIS_CAT_HASH_PRE = "cat.hash.";
    public final static String REDIS_CAT_ZSET = "cat.zset";



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
        artStateMap.put("130","Pre Assemble");
        artStateMap.put("131","Ing Assemble");
        artStateMap.put("132","Fail Assemble");
        artStateMap.put("140","Pre Deploy");
        artStateMap.put("141","Ing Deploy");
        artStateMap.put("142","Fail Deploy");
        artStateMap.put("150","Finished");
        artStateMap.put("151","Finished");
    }

    public static int updateArtState(int currentState,boolean isSuccess, int isWhole){
        String currentStateStr = currentState+"";
        int newArtState = 0;

        if(currentStateStr.endsWith("0")){
            newArtState = currentState + 1;
        }else if (currentStateStr.endsWith("2")){
            newArtState = currentState - 1;
        }else if (currentStateStr.endsWith("1")){
            if(isSuccess){
                if(isWhole == 1){
                    newArtState = currentState + 10;
                }else {
                    newArtState = currentState + 9;
                }
            }else {
                newArtState = currentState + 1;
            }
        }

        return newArtState;
    }

}
