package com.cc.lmsfc.admin.controller;

import com.cc.lmsfc.common.model.security.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Created by tomchen on 15-3-25.
 */
public abstract class BaseController {


    protected abstract String getPrefix();


    protected final String ADD = getPrefix() + "/add";
    protected final String DELETE = getPrefix() + "/delete";
    protected final String UPDATE = getPrefix() + "/update";
    protected final String EDIT = getPrefix() + "/edit";
    protected final String VIEW = getPrefix() + "/view";
    protected final String LIST = getPrefix() + "/list";


    protected Subject getCurrentUser(){
        return SecurityUtils.getSubject();
    }

    protected Session getSession(){
        return getCurrentUser().getSession();
    }

    protected User getLoginUser(){
        return (User)getSession().getAttribute("loginUser");
    }


}
