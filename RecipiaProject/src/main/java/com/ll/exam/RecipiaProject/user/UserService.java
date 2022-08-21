package com.ll.exam.RecipiaProject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void create(String username, String password, String email){
        SiteUser newUser = SiteUser.builder()
                .username(username)
                .passward(password)
                .email(email)
                .build();

        userRepository.save(newUser);
    }
}
