package com.uin;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Encoder;

import java.util.Date;

@SpringBootTest
public class SpringBootJjwtDemoApplicationTests {

    @Test
    public void createTokenTest() {
        //创建JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder()
                //声明标识{"jti":"8888"}
                .setId("8888")
                //主体，分发的对象{"sub":"uin"}
                .setSubject("uin")
                //签发的时间 创建日期{"ita":xxx""}
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "xxxxx");
        //获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);
        System.out.println("============华丽的分割线=========");
        String[] split = token.split("\\.");
        //header
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        //Payload
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        //Signature 无法解密 是乱码
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));

        //  每次加密的header和payload是一样的
        //  signature 每次都是不一样的
    }

}
