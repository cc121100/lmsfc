package com.cc.lmsfc.web.response;

import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-5-12.
 */
public class ArtAndCatJsonResponse extends BaseJsonResponse {

    private List<Map<String,String>> cList;

    private List<Map<String, String>> avs;

    public List<Map<String, String>> getcList() {
        return cList;
    }

    public void setcList(List<Map<String, String>> cList) {
        this.cList = cList;
    }

    public List<Map<String, String>> getAvs() {
        return avs;
    }

    public void setAvs(List<Map<String, String>> avs) {
        this.avs = avs;
    }
}
