# SpringBoot-JJWT-DEMO
SpringBoot整合JJWT，实现token签发

## 📖JJWT的版本

https://jwt.io/libraries?language=Java

![](https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204121431101.png)

添加依赖

```xml
 <!--JJWT的依赖-->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>
        <!--web组件-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
```
## 🧪实验步骤
1. 测试生成Token
```java
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
```
<img src="https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204121454680.png" alt="image-20220412145222343" style="zoom:50%;" />

<img src="https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204121452597.png" alt="image-20220412145245570" style="zoom:50%;" />

每次加密的header和payload是一样的,signature每次都是不一样的
