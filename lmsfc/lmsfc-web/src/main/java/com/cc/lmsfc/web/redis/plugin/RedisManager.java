package com.cc.lmsfc.web.redis.plugin;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.logging.Logger;

/**
 * Created by tomchen on 15-4-28.
 */
public class RedisManager {


    private static JedisPool jedisPool  = null;

    private String host;
    private int port;
    private String password;
    private int timeout;
    private int db;

    public RedisManager(){

    }

    public RedisManager(String host, int port, String password, int db) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.db = db;
    }

    public boolean init(){
        if(jedisPool == null){
            JedisPoolConfig jedispool_config = new JedisPoolConfig();
            jedispool_config.setMaxWaitMillis(1000 * 10);
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            jedispool_config.setMaxTotal(100);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            jedispool_config.setMaxIdle(10);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            jedispool_config.setTestOnBorrow(true);
            jedisPool = new JedisPool(jedispool_config, host, port,10,password,db);
            return true;
        }else {
            return false;
        }

    }

    public void destory(){
        jedisPool.destroy();
    }

    public static Jedis getJedis(){
        Jedis jedis = null;
        try {
            jedis =  getJedisPool().getResource();
        }catch(Exception e){
            e.printStackTrace();
        }
        return jedis;
    }

    public static void closeCon(Jedis jedis){
        if(jedis != null){
            try {
                getJedisPool().returnResource(jedis);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public static JedisPool getJedisPool() {
        return jedisPool;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }
}
