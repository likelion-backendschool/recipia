package com.ll.exam.RecipiaProject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String username, String password, String email)
            throws UsernameDuplicatedException, EmailDuplicatedException{
        SiteUser newUser = SiteUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        try{
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e){
            if (userRepository.existsByUsername(username)){
                throw new UsernameDuplicatedException("이미 사용중인 아이디입니다.");
            }
            if (userRepository.existsByEmail(email)){
                throw new EmailDuplicatedException("이미 사용중인 이메일입니다.");
            }
        }
    }
}
