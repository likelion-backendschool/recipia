package com.ll.exam.RecipiaProject.mypage;

import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;


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

    public void modify(SiteUser siteUser, String password, String email, String nickname, String gender) {

        siteUser.setPassword(password);
        siteUser.setEmail(email);
        siteUser.setNickname(nickname);
        siteUser.setGender(gender);

        userRepository.save(siteUser);
    }


}
