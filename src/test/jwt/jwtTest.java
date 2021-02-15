package jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class jwtTest {
    private Algorithm algorithm = Algorithm.HMAC256("我是密钥");
    @Test
    // 加密token
    public void encodeJWT(){
        // 过期时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expire = new Date(nowMillis + 1000 * 60); // 1s = 1000ms
        // 2.创建token
        String token = JWT.create()
                .withClaim("username","supyp") // 加密信息
                .withExpiresAt(expire) // 过期时间
                .withIssuedAt(now) // 签发时间
                .sign(algorithm); // 加密算法
        System.out.println(token);
    }
    @Test
    // 解密
    public void decodeJWT(){
        JWTVerifier verifier = JWT.require(algorithm).build();
        try{
            DecodedJWT jwt = verifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDQ4MjY2NDEsInVzZXJuYW1lIjoic3VweXAifQ.C3_18dyYCY9lHzjBswlFdtdJVf_i6OPFiQV66IrVMcg");
            System.out.println(jwt);
            System.out.println(jwt.getClaim("username").asString());
        }catch (TokenExpiredException e){
            System.out.println("过期了");
        }
    }
    @Test
    // 解密2
    public void test(){
        // 不考虑过期
        DecodedJWT decode = JWT.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDQ4MjY2NDEsInVzZXJuYW1lIjoic3VweXAifQ.C3_18dyYCY9lHzjBswlFdtdJVf_i6OPFiQV66IrVMcg");
        System.out.println(decode.getClaim("username").asString());
    }
}
