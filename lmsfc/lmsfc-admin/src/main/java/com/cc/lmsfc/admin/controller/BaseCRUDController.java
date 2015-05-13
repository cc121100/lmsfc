package com.cc.lmsfc.admin.controller;

import com.cc.lmsfc.admin.model.BjuiResponse;
import com.cc.lmsfc.common.model.BaseModel;
import com.cc.lmsfc.common.model.security.User;
import com.cc.lmsfc.common.service.BaseService;
import com.cc.lmsfc.common.util.ReflectUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * Created by tomchen on 15-3-26.
 */
public abstract class BaseCRUDController<T extends BaseModel,S extends BaseService, ID extends Serializable> extends BaseController {


    protected Class<T> modelClass;
    protected String modelName = "";
    public String modelNameLower = "";

    protected T model;

    protected BaseCRUDController() {
        modelClass =  ReflectUtils.findParameterizedType(this.getClass());
        modelName = modelClass.getSimpleName();
        modelNameLower = modelName.substring(0,1).toLowerCase() + modelName.substring(1);
    }

    /**
     * 共享的验证规则
     * 验证失败返回true
     *
     * @param t
     * @param result
     * @return
     */
    protected boolean hasError(T t, BindingResult result) {
        return result.hasErrors();
    }

    protected abstract S getService();


    @RequestMapping("/list")
    protected String list(HttpServletRequest request,Model model,@ModelAttribute T t){

        Map map = request.getParameterMap();

        String indexStr = request.getParameter("pageCurrent");
        String pageSizeStr = request.getParameter("pageSize");
        String totalCountStr = request.getParameter("totalCount");
        String order = request.getParameter("orderField");
        String orderDirection = request.getParameter("orderDirection");
        int index = 0;
        int pageSize = 20;
        int totalCount = 0;
        if(StringUtils.isNotEmpty(indexStr)){
            index = Integer.parseInt(indexStr);
            if(index > 0) index--;
        }

        if(StringUtils.isNotEmpty(pageSizeStr)){
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if(StringUtils.isNotEmpty(totalCountStr)){
            totalCount = Integer.parseInt(totalCountStr);
            if( totalCount < index * pageSize){
                index = 0;
            }
        }
        if(StringUtils.isEmpty(order)){
            order = "updatedDt";
        }
        if(StringUtils.isEmpty(orderDirection)){
            orderDirection = "desc";
        }

        PageRequest pr = new PageRequest(index,pageSize,
                "desc".equals(orderDirection) ? Sort.Direction.DESC : Sort.Direction.ASC,order);

        Page<T> pageResult =  getRecords(pr);
        model.addAttribute("pageResult",pageResult);
        model.addAttribute("orderField",order);
        model.addAttribute("orderDirection","desc".equals(orderDirection) ? "desc" : "asc");
        initWhenList(model);

        return LIST;
    }

    @RequestMapping(value = "/toEdit/{id}")
    protected String toEdit(HttpServletRequest request,@PathVariable ID id,Model m){
        Map map = request.getParameterMap();
        initModelBeforeEdit(m);
        if("new".equals(id)){

        }else{
            model = (T) getService().getById(id);
            m.addAttribute(modelNameLower,model);
        }
        m.addAttribute("operation","edit");

        return EDIT;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseBody
    protected BjuiResponse edit(HttpServletRequest request,
                              @Valid @ModelAttribute() T t,
                              BindingResult bindingResult) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        BjuiResponse br = new BjuiResponse();

        if(hasError(t,bindingResult)){

            br.setStatusCode(300);
            br.setMessage("Validate error.");
            return br;
        }

        ID id = (ID) BeanUtils.getProperty(t,"id");

        if(StringUtils.isEmpty((CharSequence) id)){//add
            BeanUtils.setProperty(t,"createdBy",getLoginUser().getUserName());
            getService().save(t);
        }else{
            BeanUtils.setProperty(t,"updatedBy",getLoginUser().getUserName());
            getService().update(t);
        }

        br.setCloseCurrent(true);
        br.setMessage("Edit " + modelNameLower + " successfully.");

        afterEdit(br,null);
        return br;

    }

    @RequestMapping(value = "/saveBatch",method = RequestMethod.POST)
    protected String saveBatch(HttpServletRequest request,Model model){
        Map map = request.getParameterMap();
        return "redirect:/" + LIST;

    }

    @RequestMapping(value = "/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseBody
    protected BjuiResponse delete(HttpServletRequest request,@PathVariable ID id){
        Map map = request.getParameterMap();
        getService().deleteById(id);
        BjuiResponse br = new BjuiResponse();
        return br;

    }

    @RequestMapping(value = "/deleteBatch")
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseBody
    protected BjuiResponse deleteBatch(HttpServletRequest request,@RequestParam(value = "delids") String[] ids){
        BjuiResponse br = new BjuiResponse();
        if(ArrayUtils.isEmpty(ids)){
            br.setStatusCode(300);
            return br;
        }
        getService().deleteByIds(ids);
        return br;
    }

    protected Page<T> getRecords(PageRequest pr){
        return getService().findAll(pr);
    }

    protected void doEdit(){

    }

    protected void initModelBeforeEdit(Model m){

    }

    protected void initWhenList(Model model) {

    }

    protected void afterEdit(BjuiResponse br, Object obj){

    }

}
