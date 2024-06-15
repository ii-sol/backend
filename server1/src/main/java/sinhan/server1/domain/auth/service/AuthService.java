package sinhan.server1.domain.auth.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.auth.dto.FamilyInfoResponse;
import sinhan.server1.domain.auth.dto.JoinInfoSaveRequest;
import sinhan.server1.domain.auth.dto.LoginInfoFindRequest;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.domain.user.entity.User;
import sinhan.server1.domain.user.repository.FamilyRepository;
import sinhan.server1.domain.user.repository.UserRepository;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private FamilyRepository familyRepository;

    @Transactional
    public UserFindOneResponse join(JoinInfoSaveRequest joinInfoSaveRequest) {
        userRepository.save(joinInfoSaveRequest.convertToUser(passwordEncoder));
        User joinUser = userRepository.findByPhoneNum(joinInfoSaveRequest.getPhoneNum())
                .orElseThrow(() -> new NoSuchElementException("가입에 실패하였습니다."));

        return joinUser.convertToUserFindOneResponse();
    }

    @Transactional
    public UserFindOneResponse login(@Valid LoginInfoFindRequest loginInfoFindRequest) throws AuthException {
        User user = userRepository.findByPhoneNum(loginInfoFindRequest.getPhoneNum())
                .orElseThrow(() -> new AuthException("INVALID_CREDENTIALS"));

        if (!passwordEncoder.matches(loginInfoFindRequest.getAccountInfo(), user.getAccountInfo())) {
            throw new AuthException("INVALID_CREDENTIALS");
        }

        return user.convertToUserFindOneResponse();
    }

    @Transactional()
    public List<FamilyInfoResponse> getFamilyInfo(long sn) {
        return familyRepository.findMyFamilyInfo(sn).stream()
                .map(fi -> new FamilyInfoResponse(fi.getSn()))
                .collect(Collectors.toList());
    }
}
