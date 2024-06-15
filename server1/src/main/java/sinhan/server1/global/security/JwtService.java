package sinhan.server1.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sinhan.server1.domain.auth.dto.FamilyInfoResponse;
import sinhan.server1.domain.auth.dto.JwtTokenResponse;
import sinhan.server1.domain.auth.dto.UserInfoResponse;
import sinhan.server1.domain.user.entity.User;
import sinhan.server1.domain.user.repository.UserRepository;
import sinhan.server1.global.security.secret.Secret;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.*;

@Service
@AllArgsConstructor
public class JwtService {

    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1800 * 1000; // 30 minutes
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 3 * 3600 * 1000; // 3 hours
    private static final String TOKEN_TYPE = "JWT";
    private UserRepository userRepository;

    private String createToken(long sn, Map<String, List<FamilyInfoResponse>> familyInfo, long expirationTime) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("typ", TOKEN_TYPE)
                .claim("sn", sn)
                .claim("familyInfo", familyInfo)
                .issuedAt(now)
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, Secret.getJwtKey())
                .compact();
    }

    public String createAccessToken(long serialNumber, Map<String, List<FamilyInfoResponse>> familyInfo) {
        return createToken(serialNumber, familyInfo, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String createRefreshToken(long serialNumber) {
        return createToken(serialNumber, null, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String getAccessToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

    public String getRefreshToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Refresh-Token");
    }

    public UserInfoResponse getUserInfo(String token) throws AuthException {
        // 1. JWT 추출
        if (token == null || token.isEmpty()) {
            throw new AuthException("EMPTY_JWT");
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        claims = Jwts.parser()
                .verifyWith(Secret.getJwtKey())
                .build()
                .parseSignedClaims(token);

        // 3. userInfo 추출
        return getUserInfoFromClaims(claims);
    }

    private UserInfoResponse getUserInfoFromClaims(Jws<Claims> claims) throws AuthException {
        String jwtType = claims.getHeader().getType();
        if (jwtType == null || (!jwtType.equals(TOKEN_TYPE))) {
            throw new AuthException("NOT_JWT");
        }

        long sn = claims.getPayload().get("sn", Long.class);
        LinkedHashMap<String, List<FamilyInfoResponse>> familyInfo = (LinkedHashMap<String, List<FamilyInfoResponse>>) claims.getPayload().get("familyInfo");
        List<FamilyInfoResponse> familyInfoResponseList = familyInfo.get("familyInfo");

//        return new UserInfoResponse(sn, familyInfo);
//        return null;
        return new UserInfoResponse(sn, familyInfoResponseList);
    }

    public Authentication getAuthentication(String token) throws AuthException {
        UserInfoResponse userInfo = getUserInfo(token);
        User user = userRepository.findBySerialNum(userInfo.getSn())
                .orElseThrow(() -> new AuthException("USER_NOT_FOUND"));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_C");

        return new UsernamePasswordAuthenticationToken(user, null, Collections.singletonList(authority));
    }

    public void sendJwtToken(HttpServletResponse httpServletResponse, JwtTokenResponse jwtTokenResponse) {
        httpServletResponse.setHeader("Authorization", "Bearer " + jwtTokenResponse.getAccessToken());
        httpServletResponse.setHeader("Refresh-Token", jwtTokenResponse.getRefreshToken());
    }

    public void sendAccessToken(HttpServletResponse httpServletResponse, String accessToken) {
        httpServletResponse.setHeader("Authorization", "Bearer " + accessToken);
    }
}