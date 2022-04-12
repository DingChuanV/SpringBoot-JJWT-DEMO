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
2. 解析token

在web应用中，这种生成token，一般都是由的服务端进行，生成token并且发送token给客户端，那么下一次客户端向服务端发请求的时候，
就会携带这个token。

这个就很像我们平时和女朋友去看电影，虽然你没有🤪 ，我们肯定要去看电影🎬软件去买票🎫，付完💰，软件给你一个二维码（服务端
），你在去线下看电影的时候，你要把票在自动取票机取出来，进去看的时候，你要拿着🎫，给检票员看票。然后你就和你女朋友看电影去了。

这里的服务端就像是检票员和软件，客户端就是你和你女朋友。电影票就是token。

```java
/**
     * 解析token
     *
     * @author wanglufei
     * @date 2022/4/12 3:08 PM
     */
    @Test
    public void paresToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoidWluIiwiaWF0IjoxNjQ5NzQ2MzU1fQ.wQFvWMy_zzqU9MDTqFERNYlbTEsByETZE2713Q8drOk";
        //解析token claims其实就是Payload的对象
        Claims claims = Jwts.parser()
                //密钥
                .setSigningKey("xxxxx")
                //把token放进去
                .parseClaimsJws(token)
                //拿到主体
                .getBody();

        System.out.println("id"+claims.getId());
        System.out.println("sub"+claims.getSubject());
        System.out.println("IssueAt"+claims.getIssuedAt());
    }
```

测试结果
```text
id8888
subuin
IssueAtTue Apr 12 14:52:35 CST 2022
```
