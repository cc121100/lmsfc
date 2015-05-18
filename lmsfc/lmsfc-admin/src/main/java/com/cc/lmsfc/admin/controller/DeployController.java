package com.cc.lmsfc.admin.controller;

import com.cc.lmsfc.admin.model.BjuiResponse;
import com.cc.lmsfc.admin.util.RedisUtils;
import com.cc.lmsfc.common.constant.CommonConsts;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by tomchen on 15-5-17.
 */
@Controller
@RequestMapping(value = "/deploy")
public class DeployController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    protected String getPrefix() {
        return "deploy";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String toDeployList(){

        return LIST;
    }

    @RequestMapping(value = "/init")
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseBody
    public BjuiResponse init(HttpServletRequest request){

        String type = request.getParameter("type");

        if(StringUtils.isEmpty(type) && !CommonConsts.REASSEMBLE_TSK.equals(type)
                && !CommonConsts.INIT_PAGE_REDIS_TSK.equals(type) && !CommonConsts.INIT_REDIS_TSK.equals(type)){
            throw new RuntimeException("type is empty or not correct!");
        }

        Map<String,Object> map = Maps.newHashMap();
        map.put("type", type);

        RedisUtils.sendTaskToRun(redisTemplate,map);

        return new BjuiResponse();
    }
}
