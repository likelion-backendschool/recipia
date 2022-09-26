package com.ll.exam.RecipiaProject.mypage;

import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public boolean checkPassword(int user_id, String checkPassword) {
        SiteUser siteUser = userRepository.findById(user_id).orElseThrow(() ->
                new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        String realPassword = siteUser.getPassword();
        boolean matches = passwordEncoder.matches(checkPassword, realPassword);
            return matches;
    }


    //id 가져오기
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    //삭제
    public void delete(SiteUser siteUser) {
        this.userRepository.delete(siteUser);
    }

    public void modify(SiteUser siteUser, String email, String nickname, String gender) {


        siteUser.setEmail(email);
        siteUser.setNickname(nickname);
        siteUser.setGender(gender);

        userRepository.save(siteUser);
    }


}