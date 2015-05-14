package com.cc.lmsfc.web.response;

import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-5-12.
 */
public class ArticleJsonResponse extends BaseJsonResponse {

    private int page;

    private int isLast;

    private List<Map<String,Object>> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public int getIsLast() {
        return isLast;
    }

    public void setIsLast(int isLast) {
        this.isLast = isLast;
    }
}
