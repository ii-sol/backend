package sinhan.server1.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.domain.user.dto.UserFindRequest;
import sinhan.server1.domain.user.entity.User;
import sinhan.server1.domain.user.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Transactional
    public UserFindOneResponse login(UserFindRequest userFindRequest) {
        User loginUser = userRepository.findByPhoneNumAndAccountInfo(
                userFindRequest.getPhoneNum(),
                userFindRequest.getAccountInfo()
        ).orElseThrow(() -> new NoSuchElementException("아이디가 존재하지 않습니다."));

        return loginUser.convertToUserFindOneResponse();
    }
}
