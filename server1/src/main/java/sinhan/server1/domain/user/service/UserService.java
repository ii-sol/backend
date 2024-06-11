package sinhan.server1.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.user.dto.*;
import sinhan.server1.domain.user.entity.Family;
import sinhan.server1.domain.user.entity.User;
import sinhan.server1.domain.user.repository.FamilyRepository;
import sinhan.server1.domain.user.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private FamilyRepository familyRepository;

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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

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

    @Transactional
    public FamilyFindOneResponse connectFamily(FamilySaveRequest familySaveRequest) {
        User familyUser = userRepository.findByPhoneNum(familySaveRequest.getPhoneNum())
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        if (familySaveRequest.getRole() == familyUser.getRole()) {
            throw new IllegalArgumentException("임시로 사용하는 에러. 부모자식 관계 불일치");
        }

        User meUser = userRepository.findById(familySaveRequest.getId()).get();

        User parents = (familySaveRequest.getRole() == 1) ? meUser : familyUser;
        User child = (familySaveRequest.getRole() == 1) ? familyUser : meUser;

        return familyRepository.save(new Family(parents, child)).convertToFamilyFindOneResponse();
    }

    @Transactional
    public void disconnectFamily(int parentsId, int childId) {
        Optional<Family> family = Optional.ofNullable(familyRepository.findByParentsIdAndChildId(parentsId, childId)
                .orElseThrow(() -> new NoSuchElementException("가족 관계가 존재하지 않습니다.")));

        familyRepository.delete(family.get().getId());
    }
}
