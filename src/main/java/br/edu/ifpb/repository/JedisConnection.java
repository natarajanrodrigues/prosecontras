package br.edu.ifpb.repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by kieckegard on 02/09/2016.
 */
public class JedisConnection {

    private static JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

    public static Jedis getJedisConnection() {
        return pool.getResource();
    }

}
