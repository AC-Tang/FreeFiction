package com.example.freefiction.utils.jjwt;

import com.example.freefiction.entity.UserLogin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JJwtUtil {
    Logger log = LoggerFactory.getLogger(getClass());
    private static final long REFRESH_THRESHOLD = 10 * 60 * 1000L;  // 剩余10分钟自动续期
    @Autowired
    JJwtProperties jwtProperties;

    /**
     * 生成token
     * @param user
     * @return
     */
    public String sign(UserLogin user){
        // 生成令牌ID
        String uuid = UUID.randomUUID().toString();

        SecureDigestAlgorithm<SecretKey,SecretKey> algorithm= Jwts.SIG.HS256;
        long expMillis=System.currentTimeMillis()+jwtProperties.getExpiration();
        Date exp=new Date(expMillis);

        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));

        String token=Jwts.builder()
                .id(uuid)  //令牌ID
                .claim("userid",user.getId())
                .claim("role",user.getAvatar())
                .signWith(key,algorithm)
                .expiration(exp)  //过期时间
                .issuedAt(new Date())  //签发时间
                .compact();

        return token;


    }

    /**
     * token验证
     * @param token
     * @return
     */
    public Boolean verify(String token){
        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));

        try{
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 获取payload中的用户信息
     *
     * @param token JWT Token
     * @return 用户信息
     */
    public String getUserFromToken(String token) {
        String user = "";
        Claims claims = parseClaims(token);
        if (claims != null) {
            user = (String) claims.get("userid");
        }
        return user;
    }

    /**
     * 解析JWT Token中的Claims
     *
     * @param token JWT Token
     * @return Claims
     */
    public Claims parseClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));

        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean shouldRefresh(Date expiration) {
        long remaining = expiration.getTime() - System.currentTimeMillis();
        return remaining < REFRESH_THRESHOLD;
    }

    public String generateToken(String userid, String role) {
        // 生成令牌ID
        String uuid = UUID.randomUUID().toString();
        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        long expMillis=System.currentTimeMillis()+jwtProperties.getExpiration();
        Date exp=new Date(expMillis);
        SecureDigestAlgorithm<SecretKey,SecretKey> algorithm= Jwts.SIG.HS256;

        String token=Jwts.builder()
                .id(uuid)  //令牌ID
                .claim("userid",userid)
                .claim("role",role)
                .signWith(key,algorithm)
                .expiration(exp)  //过期时间
                .issuedAt(new Date())  //签发时间
                .compact();

        return token;
    }
}
