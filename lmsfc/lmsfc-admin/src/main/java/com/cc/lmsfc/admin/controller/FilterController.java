package com.cc.lmsfc.admin.controller;

import com.cc.lmsfc.common.model.filter.Filter;
import com.cc.lmsfc.common.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomchen on 15-3-26.
 */
@Controller
@RequestMapping("/filter")
public class FilterController extends BaseCRUDController<Filter,FilterService,String>{

    @Autowired
    private FilterService filterService;

    @Override
    protected String getPrefix() {
        return "filter";
    }

    @Override
    protected FilterService getService() {
        return filterService;
    }

    @InitBinder("filter")
    public void initBinder1(WebDataBinder binder) {
        binder.setFieldDefaultPrefix(modelNameLower + ".");
    }
}
