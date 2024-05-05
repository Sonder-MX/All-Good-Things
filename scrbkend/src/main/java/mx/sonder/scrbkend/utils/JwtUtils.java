package mx.sonder.scrbkend.utils;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import mx.sonder.scrbkend.entity.Users;
import mx.sonder.scrbkend.entity.vo.TokenVO;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expire}")
    private int expire;

    public TokenVO createJwt(Users user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Date expTime = this.expireTime();
        String signStr = JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("username", user.getUsername())
                .withClaim("email", user.getEmail())
                .withClaim("github", "Sonder-MX")
                .withExpiresAt(expTime)
                .withIssuedAt(new Date())
                .sign(algorithm);
        TokenVO token = new TokenVO();
        token.setUsername(user.getUsername());
        token.setToken("Bearer " + signStr);
        token.setExpire(expTime);
        return token;
    }

    public DecodedJWT verifyJwt(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        token = token.substring(7);
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT parseToken = jwtVerifier.verify(token);
            if (parseToken.getExpiresAt().before(new Date())) {
                return null;
            }
            return parseToken;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Date expireTime() {
        return new Date(System.currentTimeMillis() + this.expire * 1000);
    }
}
