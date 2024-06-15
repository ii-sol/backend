package sinhan.server1.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.user.dto.FamilyFindOneResponse;
import sinhan.server1.domain.user.dto.FamilySaveRequest;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.domain.user.dto.UserUpdateRequest;
import sinhan.server1.domain.user.entity.Family;
import sinhan.server1.domain.user.entity.User;
import sinhan.server1.domain.user.repository.FamilyRepository;
import sinhan.server1.domain.user.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private FamilyRepository familyRepository;

    @Transactional
    public UserFindOneResponse getUser(long sn) {
        return findUser(sn).convertToUserFindOneResponse();
    }

    @Transactional
    public UserFindOneResponse updateUser(UserUpdateRequest userUpdateRequest) {
        User user = findUser(userUpdateRequest.getSerialNum());

        user.setPhoneNum(userUpdateRequest.getPhoneNum());
        user.setName(userUpdateRequest.getName());
        user.setBirthDate(userUpdateRequest.getBirthdate());
        user.setProfileId(userUpdateRequest.getProfileId());

        User updatedUser = userRepository.save(user);

        return getUser(updatedUser.getSerialNum());
    }

    @Transactional
    public FamilyFindOneResponse connectFamily(FamilySaveRequest familySaveRequest) {
        User user = findUser(familySaveRequest.getUserSn());

        return familyRepository.save(new Family(user, familySaveRequest.getFamilySn())).convertToFamilyFindOneResponse();
    }

    @Transactional
    public boolean disconnectFamily(long sn, long familySn) {
        User user = findUser(sn);

        Family family = familyRepository
                .findByUserSerialNumAndFamilySn(user, familySn)
                .orElseThrow(() -> new NoSuchElementException("가족 관계가 존재하지 않습니다."));

        familyRepository.delete(family.getId());

        Optional<Family> checkFamily = familyRepository.findById(family.getId());
        return checkFamily.isEmpty();
    }

    @Transactional
    public List<String> getPhones() {
        return userRepository.findAllPhones();
    }

    private User findUser(long sn) {
        return userRepository.findBySerialNum(sn)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));
    }
}
