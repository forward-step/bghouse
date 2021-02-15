package com.supyp.bghouse.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyp.bghouse.configuration.Config;
import com.supyp.bghouse.domain.entity.Account;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class JWTUtil {

    @Resource
    public void setJSON(ObjectMapper objectMapper){
        JWTUtil.JSON = objectMapper;
    }
    private static ObjectMapper JSON; // JackJSON

    // 加密信息
    public static String encodeJWT(Object info){
        Algorithm algorithm = Algorithm.HMAC256(Config.SECRET); // 加密算法
        // 过期时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expire = new Date(nowMillis + Config.expireTime);
        // 将对象转换位JSN字符串
        String jsonString = null;
        try {
            jsonString = JSON.writeValueAsString(info);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 创建token
        String token = JWT.create()
                .withClaim("data",jsonString) // 加密信息
                .withExpiresAt(expire) // 过期时间
                .withIssuedAt(now) // 签发时间
                .sign(algorithm); // 加密算法
        return token;
    }

    // 解密信息
    public static Account decodeJWT(String token){
        Algorithm algorithm = Algorithm.HMAC256(Config.SECRET); // 加密算法
        JWTVerifier verifier = JWT.require(algorithm).build();
        try{
            DecodedJWT jwt = verifier.verify(token);
            String jsonString = jwt.getClaim("data").asString();
            return JSON.readValue(jsonString, Account.class);
        }catch (TokenExpiredException e){
            return null; // 过期了
        } catch (JsonMappingException e) {
            e.printStackTrace(); // json 2 obj 转换失败
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (JWTDecodeException e){
            // 解析token失败，也就是说token是无效的，伪造的
            return null;
        }
        return null;
    }
}
