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

    private static final long EXPIRATION_TIME = 1800 * 1000; // 30 minutes
    private static final String TOKEN_TYPE = "JWT";

    public String createParentJwt(String phoneNum, Map<String, String> children) {
        return createJwt(phoneNum, children, "parent");
    }

    public String createChildJwt(String phoneNum, Map<String, String> parents) {
        return createJwt(phoneNum, parents, "child");
    }

    private String createJwt(String phoneNum, Map<String, String> claims, String userType) {
        Date now = new Date();
        return Jwts.builder()
            .claim("phoneNum", phoneNum)
            .claim(userType, claims)
            .setIssuedAt(now)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
            .compact();
    }
}