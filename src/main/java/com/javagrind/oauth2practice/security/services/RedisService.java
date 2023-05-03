package com.javagrind.oauth2practice.security.services;

import com.javagrind.oauth2practice.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

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

        if (token != null) {
            System.err.println(token);
            Long expDate = jwtUtils.getExpiredAt(token);
            Long dateNow = (System.currentTimeMillis() / 1000);

            if ((expDate > dateNow) && jwtUtils.validateJwtToken(token))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
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