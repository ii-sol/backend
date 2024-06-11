package sinhan.server1.domain.allowance.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.allowance.dto.RegularAllowanceSaveRequestDto;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AllowanceService {
    public void createRegularAllowance(int userId, int parentId, RegularAllowanceSaveRequestDto request) {
        //생성된 후 부터, period동안 달마다 createdate의 오후 1시 반복 user->children
        // request에 period, amount
        // jwt랑 fo => child(reciever) & jwt안에 있는 userId => parent
        // parent => child period 동안 달마다 createDate의 1시마다 amount 보냄
        // schedule이 겹칠수도 있음
    }
}
