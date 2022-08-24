package com.boss.demo.tools;


import com.boss.demo.security.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 */
@Component
public class JwtTokenUtil implements Serializable {

    /**
     * 5天(毫秒)
     */
    private static final long EXPIRATION_TIME = 432000000;
    /**
     * JWT密码
     */
    private static final String SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 签发JWT
     */
    public String generateToken(long id,String username,String name) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put( "id", id );
        claims.put( "username", username );
        claims.put( "name", username );
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setClaims( claims )
                .setExpiration( new Date( Instant.now().toEpochMilli() + EXPIRATION_TIME  ) )
                .signWith( SignatureAlgorithm.HS512, SECRET )
                .compact();
    }

    /**
     * 验证JWT
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        CustomUser customUser = (CustomUser) userDetails;
        String username = getUsernameFromToken( token );
        return (username.equals( customUser.getUsername() ) && !isTokenExpired( token ));
    }

    /**
     * 获取token是否过期
     */
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken( token );
        return expiration.before( new Date() );
    }

    /**
     * 根据token获取username
     */
    public String getUsernameFromToken(String token) {
        String username = (String)getClaimsFromToken( token ).get("username");
        return username;
    }

    /**
     * 获取token的过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration = getClaimsFromToken( token ).getExpiration();
        return expiration;
    }

    /**
     * 解析JWT
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey( SECRET )
                .parseClaimsJws( token )
                .getBody();
        return claims;
    }



}