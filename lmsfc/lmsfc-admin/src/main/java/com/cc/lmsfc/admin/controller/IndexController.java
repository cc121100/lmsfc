package com.cc.lmsfc.admin.controller;

import com.cc.lmsfc.common.model.security.Resource;
import com.cc.lmsfc.common.model.security.Role;
import com.cc.lmsfc.common.model.security.User;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tomchen on 15-3-23.
 */
@Controller
public class IndexController extends BaseController {

    Logger logger = Logger.getLogger(IndexController.class);

    @RequestMapping(value = "/")
    public String redirecTtoIndex(){
        return "index";
    }

    @RequestMapping(value = "/index")
    public ModelAndView toIndex(){
        User loginUser = (User) getSession().getAttribute("loginUser");
        System.err.println(loginUser.getUserName());

        Set<Resource> resources = new LinkedHashSet<>();
        for(Role r : loginUser.getRoles()){
            for(Resource re : r.getResources()){
                if ("menu".equals(re.getType()) || "subMenu".equals(re.getType())){
                    resources.add(re);
                }
            }
        }

        ModelAndView mv = new ModelAndView("index");
        mv.addObject("menus",resources);
        return mv;
    }

    @RequestMapping(value = "/login")
    public String toLogin(HttpServletRequest req, Model model){
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "login";
    }

    @Override
    protected String getPrefix() {
        return null;
    }
}
