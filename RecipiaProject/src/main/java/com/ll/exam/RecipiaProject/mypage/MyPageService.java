package com.ll.exam.RecipiaProject.mypage;

import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    //id 가져오기
    public SiteUser getUser(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no %d user not found,".formatted(id)));
    }

    //삭제
    public void delete(SiteUser siteUser) {
        this.userRepository.delete(siteUser);
    }
}
