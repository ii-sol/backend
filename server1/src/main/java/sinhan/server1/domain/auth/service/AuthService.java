package sinhan.server1.domain.auth.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.auth.dto.LoginInfoFindRequest;
import sinhan.server1.domain.auth.repository.AuthRepository;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.domain.user.entity.User;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private AuthRepository authRepository;

    @Transactional
    public UserFindOneResponse login(@Valid LoginInfoFindRequest loginInfoFindRequest) {
        User loginUser = authRepository.findByPhoneNumAndAccountInfo(
                loginInfoFindRequest.getPhoneNum(),
                loginInfoFindRequest.getAccountInfo()
        ).orElseThrow(() -> new NoSuchElementException("아이디가 존재하지 않습니다."));

        return loginUser.convertToUserFindOneResponse();
    }

    public List<Map<Integer, String>> getFamily(int id) {

        return null;
    }
}
