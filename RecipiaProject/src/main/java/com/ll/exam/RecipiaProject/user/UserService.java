package com.ll.exam.RecipiaProject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String username, String password, String email){
        SiteUser newUser = SiteUser.builder()
                .username(username)
                .passward(passwordEncoder.encode(password))
                .email(email)
                .build();

        userRepository.save(newUser);
    }
}
