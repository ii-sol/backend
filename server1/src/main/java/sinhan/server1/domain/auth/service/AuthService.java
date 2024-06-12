package sinhan.server1.domain.auth.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.auth.dto.FamilyInfoInterface;
import sinhan.server1.domain.auth.dto.LoginInfoFindRequest;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.domain.user.entity.User;
import sinhan.server1.domain.user.repository.FamilyRepository;
import sinhan.server1.domain.user.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private FamilyRepository familyRepository;

    @Transactional
    public UserFindOneResponse login(@Valid LoginInfoFindRequest loginInfoFindRequest) {
        User loginUser = userRepository.findByPhoneNumAndAccountInfo(loginInfoFindRequest.getPhoneNum(), loginInfoFindRequest.getAccountInfo())
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        return loginUser.convertToUserFindOneResponse();
    }

    @Transactional
    public List<FamilyInfoInterface> getFamilyInfo(int id) {
        return familyRepository.findMyFamilyInfo(id);
    }
}
