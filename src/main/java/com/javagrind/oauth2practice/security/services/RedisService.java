package com.javagrind.oauth2practice.security.services;

import com.javagrind.oauth2practice.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;
import java.util.Date;

@Service()
@RequiredArgsConstructor
public class RedisService {

    @Autowired
    JwtUtils jwtUtils;
    JedisPooled jedis = new JedisPooled("localhost", 6379);

    public void store(String token){
        String email = jwtUtils.getUserNameFromJwtToken(token);
        jedis.set(email, token);
    }

    public Boolean isThere(String email){
        String token = jedis.get(email);

        if(token == null) {
            return Boolean.FALSE;
        } else {
            Date now = new Date((new Date()).getTime());
            if(now.compareTo(jwtUtils.getExpiredAt(token)) > 0){
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
    }

    public Boolean destroyToken(String email){
        String token = jedis.get(email);

        if(token == null) {
            return Boolean.FALSE;
        }else {
            jedis.del(email);
        }
        return Boolean.TRUE;
    }

}