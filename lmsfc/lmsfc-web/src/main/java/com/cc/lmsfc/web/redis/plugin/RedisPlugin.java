package com.cc.lmsfc.web.redis.plugin;

import com.jfinal.plugin.IPlugin;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by tomchen on 15-4-28.
 */
public class RedisPlugin implements IPlugin {

    private RedisManager redisManager;

    public RedisPlugin(String host,int port, String password, int db){
        redisManager = new RedisManager(host,port,password,db);
    }

    @Override
    public boolean start() {
        return redisManager.init();
    }

    @Override
    public boolean stop(){
        redisManager.destory();
        return true;
    }

    public static void main(String[] args){
        IPlugin ip = new RedisPlugin("127.0.0.1",6379,"password",0);
        ip.start();

        Set<String> set = RedisKit.keys("*");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        ip.stop();
    }
}
