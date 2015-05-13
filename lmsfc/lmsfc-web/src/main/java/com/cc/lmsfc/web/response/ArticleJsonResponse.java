package com.cc.lmsfc.web.response;

import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-5-12.
 */
public class ArticleJsonResponse extends BaseJsonResponse {

    private int page;

    private List<Map<String,String>> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }
}
