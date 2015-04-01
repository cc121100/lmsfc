package com.cc.lmsfc.admin.controller;

import com.cc.lmsfc.common.model.filter.FilterDetail;
import com.cc.lmsfc.common.service.FilterDetailService;
import com.cc.lmsfc.common.service.FilterRuleService;
import com.cc.lmsfc.common.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by tomchen on 15-3-29.
 */
@Controller
@RequestMapping("/filterDetail")
public class FilterDetailController extends BaseCRUDController<FilterDetail, FilterDetailService, String>{

    @Autowired
    private FilterDetailService filterDetailService;

    @Autowired
    private FilterService filterService;

    @Autowired
    private FilterRuleService filterRuleService;



    @Override
    protected String getPrefix() {
        return "filterDetail";
    }

    @Override
    protected FilterDetailService getService() {
        return filterDetailService;
    }

    @InitBinder("filterDetail")
    public void initBinder1(WebDataBinder binder) {
        binder.setFieldDefaultPrefix(modelNameLower + ".");
    }

    @Override
    protected Page<FilterDetail> getRecords(PageRequest pr) {
        return super.getRecords(pr);
    }

    @Override
    protected void initModelBeforeEdit(Model m) {
        m.addAttribute("filterList",filterService.findAll());
        m.addAttribute("filterRuleList",filterRuleService.findAll());
        m.addAttribute("parentNodeList",filterDetailService.findAll());

    }
}
