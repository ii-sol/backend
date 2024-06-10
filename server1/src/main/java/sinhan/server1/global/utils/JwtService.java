package sinhan.server1.global.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sinhan.server1.global.utils.exception.AuthException;
import sinhan.server1.global.utils.secret.Secret;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1800 * 1000; // 30 minutes
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 3 * 3600 * 1000; // 3 hours
    private static final String TOKEN_TYPE = "JWT";

    public String createAccessToken(String userId, Map<String, String> claims, String familyType) {
        return createToken(userId, claims, familyType, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String createRefreshToken(String userId, Map<String, String> claims, String familyType) {
        return createToken(userId, claims, familyType, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String createToken(String userId, Map<String, String> claims, String familyType,
                               long expirationTime) {
        Date now = new Date();
        return Jwts.builder()
                .claim("userId", userId)
                .claim(familyType, claims)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    public String getAccessToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("accessToken");
    }

    public String getRefreshToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("refreshToken");
    }

    public Map<String, Object> getUserInfo() throws AuthException {
        // 1. JWT 추출
        String accessToken = getAccessToken();
        if (accessToken == null || accessToken.isEmpty()) {
            throw new AuthException("EMPTY_JWT");
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception e) {
            throw new AuthException("INVALID_JWT");
        }

        // 3. userIdx 추출
        return getUserInfoFromClaims(claims);
    }

    private Map<String, Object> getUserInfoFromClaims(Jws<Claims> claims) throws AuthException {
        Map<String, Object> userInfo = new HashMap<>();

        String jwtType = claims.getBody().get("typ", String.class);
        if (jwtType == null || (!jwtType.equals("JWT"))) {
            throw new AuthException("INVALID_JWT");
        }

        int role = claims.getBody().get("role", Integer.class);
        userInfo.put("role", role);

        int userId = claims.getBody().get("userId", Integer.class);
        userInfo.put("userId", userId);

        List<Map<Integer, String>> familyInfo = claims.getBody().get("familyInfo", List.class);
        userInfo.put("familyInfo", familyInfo);

        return userInfo;
    }
}