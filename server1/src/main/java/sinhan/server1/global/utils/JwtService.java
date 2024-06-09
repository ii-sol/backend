package sinhan.server1.global.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import sinhan.server1.global.utils.secret.Secret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private static final long EXPIRATION_TIME = 1800 * 1000; // 30분
    private static final String TOKEN_TYPE = "JWT";

    private String createJwt(Map<String, Object> claims) {

        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
            .compact();
    }

    public String createParentJwt(String phoneNum, Map<String, String> children) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNum", phoneNum);
        claims.put("children", children);
        claims.put("type", TOKEN_TYPE);
        return createJwt(claims);
    }

    public String createChildJwt(String phoneNum, Map<String, String> parents) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNum", phoneNum);
        claims.put("parents", parents);
        claims.put("type", TOKEN_TYPE);
        return createJwt(claims);
    }
}