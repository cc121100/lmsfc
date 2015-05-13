package com.cc.lmsfc.web.response;

import java.util.Date;

/**
 * Created by tomchen on 15-5-12.
 */
public class BaseJsonResponse {

    private int stateCode;

    private String msg;

    private Date date;

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
