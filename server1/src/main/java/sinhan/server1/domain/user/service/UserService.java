package sinhan.server1.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.domain.user.dto.UserFindRequest;
import sinhan.server1.domain.user.dto.UserUpdateRequest;
import sinhan.server1.domain.user.entity.User;
import sinhan.server1.domain.user.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {

//    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserFindOneResponse login(UserFindRequest userFindRequest) {
        User loginUser = userRepository.findByPhoneNumAndAccountInfo(
                userFindRequest.getPhoneNum(),
                userFindRequest.getAccountInfo()
        ).orElseThrow(() -> new NoSuchElementException("아이디가 존재하지 않습니다."));

        return loginUser.convertToUserFindOneResponse();
    }

    @Transactional
    public UserFindOneResponse getUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        return user.convertToUserFindOneResponse();
    }

    @Transactional
    public UserFindOneResponse updateUser(UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userUpdateRequest.getId())
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        user.setPhoneNum(userUpdateRequest.getPhoneNum());
        user.setName(userUpdateRequest.getName());
        user.setBirthdate(userUpdateRequest.getBirthdate());
        user.setProfileId(userUpdateRequest.getProfileId());

        User updatedUser = userRepository.save(user);

        return getUser(updatedUser.getId());
    }
}
