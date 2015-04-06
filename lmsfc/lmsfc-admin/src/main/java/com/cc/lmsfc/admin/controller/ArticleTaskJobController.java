package com.cc.lmsfc.admin.controller;

import com.cc.lmsfc.admin.model.BjuiResponse;
import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.model.filter.FilterDetail;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.common.service.ArticleCategoryService;
import com.cc.lmsfc.common.service.ArticleTaskJobService;
import com.cc.lmsfc.common.service.FilterRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomchen on 15-3-30.
 */
@Controller
@RequestMapping("/articleTask")
public class ArticleTaskJobController extends BaseCRUDController<ArticleTaskJob,ArticleTaskJobService,String> {

    @Autowired
    private ArticleTaskJobService articleTaskJobService;

    @Autowired
    private FilterRuleService filterRuleService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    @Qualifier("adminJmsTemplate")
    private JmsTemplate jmsTemplate;

    @Override
    protected ArticleTaskJobService getService() {
        return articleTaskJobService;
    }

    @RequestMapping(value = "/runTask/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseBody
    public BjuiResponse runTask(HttpServletRequest request,@PathVariable String id){
        BjuiResponse br = new BjuiResponse();
        //update state
        articleTaskJobService.updateState(id,false);

        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("type",CommonConsts.SINGLE_TSK);
        sendTaskToRun(map);

        br.setMessage("Task is running, please wait!");
        return br;
    }

    @RequestMapping(value = "/view/{id}")
    protected String toView(HttpServletRequest request,@PathVariable String id,Model m){
        ArticleTaskJob atj = articleTaskJobService.getById(id);
        m.addAttribute("articleTaskJob", atj);
        initWhenList(m);

        return VIEW;
    }

    @Override
    protected BjuiResponse edit(HttpServletRequest request, @Valid @ModelAttribute() ArticleTaskJob articleTaskJob, BindingResult bindingResult) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        BjuiResponse bjuiResponse =  super.edit(request, articleTaskJob, bindingResult);

        String isStartedNow = request.getParameter("isStartedNow");
        if("Y".equals(isStartedNow)){
            Map<String,Object> map = new HashMap<>();
            map.put("id",articleTaskJob.getId());
            map.put("type",CommonConsts.SINGLE_TSK);
            sendTaskToRun(map);
        }
        return bjuiResponse;
    }

    @Override
    protected String getPrefix() {
        return "articleTask";
    }

    @Override
    protected void initWhenList(Model model) {
        model.addAttribute("artStateMap", CommonConsts.artStateMap);
    }

    @InitBinder("articleTaskJob")
    public void initBinder1(WebDataBinder binder) {
        binder.setFieldDefaultPrefix(modelNameLower + ".");
    }

    @Override
    protected void initModelBeforeEdit(Model m) {
        m.addAttribute("filterRuleList",filterRuleService.findAll());
        m.addAttribute("artCategoryList",articleCategoryService.findAll());
    }

    @Override
    protected void afterEdit(BjuiResponse br,Object obj) {
        //put in jms
//        sendTaskToRun((Map<String, Object>) obj);
        br.setMessage(br.getMessage() + " Task is running, please wait!");
    }

    private void sendTaskToRun(final Map<String,Object> map){
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage((java.io.Serializable) map);
            }
        });
    }

}
