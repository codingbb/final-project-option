package com.example.finalprojectdtomarket.user;


import com.example.finalprojectdtomarket._core.errors.exception.LoginFailException;
import com.example.finalprojectdtomarket._core.errors.exception2.Exception401;
import com.example.finalprojectdtomarket._core.errors.exception2.Exception404;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJPARepository userRepo;

    //로그인 용
    public UserResponse.LoginDTO login(UserRequest.LoginDTO requestDTO) {
        User sessionUser = userRepo.findByUsernameAndPassword(requestDTO.getUsername(), requestDTO.getPassword())
                .orElseThrow(() -> new LoginFailException());

        //sessionUser.getRole 에서 2가 들어오면 true (2면 유저) 유저냐? 예스예스
        Boolean isCheck = sessionUser.getRole() == 2;

        return new UserResponse.LoginDTO(sessionUser, isCheck);

    }

    //회원정보 수정 페이지 없음
    //회원정보수정
    public User findById(int id, UserRequest.UpdateDTO reqDTO) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다."));
        user.setPassword(reqDTO.getPassword());
        return user; //더티체킹

    }

    // 회원가입 중복체크
    public User getUsername(String username) {
        return userRepo.findByUsername(username);
    }


    @Transactional
    public UserResponse.JoinDTO joinUser(UserRequest.JoinDTO reqDTO) {
        User user = userRepo.save(reqDTO.toEntity());
        user.setRole(2);
        userRepo.save(user);
        return new UserResponse.JoinDTO(user);
    }

    // user에서 쓰는 거 아님
    public User findUserId(Integer id) {

        System.out.println("10. UserService에 findUserId 실행함");

        User user = userRepo.findById(id)
                .orElseThrow(() -> new Exception404("존재하지 않는 사용자 입니다"));

        return user;
    }
}
