package com.cc.lmsfc.admin.controller;

import com.cc.lmsfc.common.model.filter.Filter;
import com.cc.lmsfc.common.model.filter.FilterRule;
import com.cc.lmsfc.common.service.FilterRuleService;
import com.cc.lmsfc.common.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomchen on 15-3-28.
 */
@Controller
@RequestMapping(value = "/filterRule")
public class FilterRuleController extends BaseCRUDController<FilterRule,FilterRuleService,String>{

    @Autowired
    private FilterRuleService filterRuleService;


    @Override
    protected FilterRuleService getService() {
        return filterRuleService;
    }

    @Override
    protected String getPrefix() {
        return "filterRule";
    }

    @InitBinder("filterRule")
    public void initBinder1(WebDataBinder binder) {
        binder.setFieldDefaultPrefix(modelNameLower + ".");
    }
}
