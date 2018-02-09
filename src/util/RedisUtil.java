package util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

public class RedisUtil {

   // private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private JedisPool pool = null;

    public static RedisUtil gsInstance = new RedisUtil();


    public void init(int maxIdle, int maxTotal, int maxWaitMillis, String host, int port) {
       // logger.debug("Initialized RedisUtil with host: " + host + ", port: " + String.valueOf(port) + ", maxIdle: " + String.valueOf(maxIdle) + ", maxTotal: " + String.valueOf(maxTotal) + ", maxWaitMillis: " + String.valueOf(maxWaitMillis));
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis);

        pool = new JedisPool(poolConfig, host, port, maxWaitMillis);
    }

    public void init(int maxIdle, int maxTotal, int maxWaitMillis, String host, int port, String password) {
        //logger.debug("Initialized RedisUtil with host: " + host + ", port: " + String.valueOf(port) + ", maxIdle: " + String.valueOf(maxIdle) + ", maxTotal: " + String.valueOf(maxTotal) + ", maxWaitMillis: " + String.valueOf(maxWaitMillis));
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis);

        if (password.equals("")) {
            pool = new JedisPool(poolConfig, host, port, maxWaitMillis);
        } else {
            pool = new JedisPool(poolConfig, host, port, maxWaitMillis, password);
        }
    }

    private Jedis getClient() {
        if (pool == null) {
            //logger.debug("Uninitialize RedisUtil!!!!!");
            return null;
        }
        return pool.getResource();
    }

    /**
     * key operations
     */
    public String get(String redisKey) {
        
    	String aesKey = null;
        try {
            aesKey = get0(redisKey);
        } catch (Exception e) {
           // logger.info("redis get data error");
            e.printStackTrace();
        }
        
        return aesKey;
    }
    
    public String get0(String redisKey) throws Exception{
        Jedis jedis = null;
        String aesKey = null;
        try {
            jedis = getClient();
            aesKey = jedis.get(redisKey);
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) jedis.close();
        }
        return aesKey;
    }

    public void set(String key, String value) {
        try {
            set0(key, value);
        } catch (Exception e) {
           // logger.info("redis set data error");
            e.printStackTrace();
        } 

    }

    public void set0(String key, String value) throws Exception{
        Jedis jedis = null;
        try {
            jedis = getClient();
            jedis.set(key, value);
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) jedis.close();
        }
    }

}
