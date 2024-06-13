package sinhan.server1.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sinhan.server1.domain.auth.dto.FamilyInfoInterface;
import sinhan.server1.domain.auth.dto.UserInfoResponse;
import sinhan.server1.domain.user.entity.User;
import sinhan.server1.domain.user.repository.UserRepository;
import sinhan.server1.global.utils.exception.AuthException;
import sinhan.server1.global.security.secret.Secret;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1800 * 1000; // 30 minutes
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 3 * 3600 * 1000; // 3 hours
    private static final String TOKEN_TYPE = "JWT";
    @Autowired
    private UserRepository userRepository;

    private String createToken(Integer role, int userId, Map<String, List<FamilyInfoInterface>> familyInfo, long expirationTime) {
        Date now = new Date();
        return Jwts.builder()
                .claim("role", role)
                .claim("userId", userId)
                .claim("familyInfo", familyInfo)
                .issuedAt(now)
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, Secret.getJwtKey())
                .compact();
    }

    public String createAccessToken(int role, int userId, Map<String, List<FamilyInfoInterface>> familyInfo) {
        return createToken(role, userId, familyInfo, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String createRefreshToken(int userId) {
        return createToken(null, userId, null, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String getAccessToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

    public String getRefreshToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Refresh-Token");
    }

    public UserInfoResponse getUserInfo() throws AuthException {
        // 1. JWT 추출
        String accessToken = getAccessToken();
        if (accessToken == null || accessToken.isEmpty()) {
            throw new AuthException("EMPTY_JWT");
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(Secret.getJwtKey())
                    .build()
                    .parseSignedClaims(accessToken);
        } catch (Exception e) {
            throw new AuthException("INVALID_JWT");
        }

        // 3. userInfo 추출
        return getUserInfoFromClaims(claims);
    }

    private UserInfoResponse getUserInfoFromClaims(Jws<Claims> familyInfo) throws AuthException {
        String jwtType = familyInfo.getBody().get("typ", String.class);
        if (jwtType == null || (!jwtType.equals("JWT"))) {
            throw new AuthException("INVALID_JWT");
        }

        int role = familyInfo.getBody().get("role", Integer.class);
        int userId = familyInfo.getBody().get("userId", Integer.class);
        List<FamilyInfoInterface> familyInfoResponse = familyInfo.getBody().get("familyInfo", List.class);

        return new UserInfoResponse(role, userId, familyInfoResponse);
    }

    public Authentication getAuthentication(String token) throws AuthException {
        UserInfoResponse userInfo = getUserInfo();
        User user = userRepository.findById(userInfo.getUserId())
                .orElseThrow(() -> new AuthException("USER_NOT_FOUND"));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());

        return new UsernamePasswordAuthenticationToken(user, "", Collections.singletonList(authority));
    }
}