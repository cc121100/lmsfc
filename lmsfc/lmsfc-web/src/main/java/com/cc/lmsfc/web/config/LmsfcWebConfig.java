package com.cc.lmsfc.web.config;

import com.cc.lmsfc.web.controller.IndexController;
import com.cc.lmsfc.web.redis.plugin.RedisPlugin;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

import java.util.Date;

/**
 * Created by tomchen on 15-4-24.
 */
public class LmsfcWebConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
        loadPropertyFile("a_little_config.txt");
//        loadPropertyFile("classes/a_little_config.txt");
//        me.setDevMode(getPropertyToBoolean("devMode", false));
        System.out.println("============LmsfcWebConfig============");
        me.setDevMode(true);
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", IndexController.class, "/index");	// 第三个参数为该Controller的视图存放路径
//        me.add("/blog", BlogController.class);			// 第三个参数省略时默认与第一个参
    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://127.0.0.1/lmsfc?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull"
                                ,"root","password");
        me.add(cp);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
        me.add(arp);

        me.add(new RedisPlugin(getProperty("redisHost"),getPropertyToInt("redisPort"),getProperty("redisPassword"),getPropertyToInt("redisDB")));
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {

    }

    public static void main(String[] args){

        JFinal.start("lmsfc/lmsfc-web/src/main/webapp", 8090, "/lmsfc-web", 5);
    }

}
