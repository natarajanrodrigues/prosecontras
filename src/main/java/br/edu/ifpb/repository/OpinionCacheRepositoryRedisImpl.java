package br.edu.ifpb.repository;

import br.edu.ifpb.entity.Opinion;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * Created by kieckegard on 02/09/2016.
 */
@Repository
public class OpinionCacheRepositoryRedisImpl implements OpinionCacheRepository {

    @Override
    public void save(Opinion opinion) {
        try (Jedis jedis = JedisConnection.getJedisConnection()) {
            jedis.set(opinion.getUser().getEmail(), opinion.getJsonString());

            jedis.save();
        }
    }

    @Override
    public void remove(String userEmail) {
        try (Jedis jedis = JedisConnection.getJedisConnection()) {
            jedis.del(userEmail);
        }
    }

    @Override
    public String getCachedOpinion(String userEmail) {
        try (Jedis jedis = JedisConnection.getJedisConnection()) {
            return jedis.get(userEmail);
        }
    }
}
