package com.ll.exam.RecipiaProject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

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

    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void sendEmail(String email) {
        String addr = "gi020477@gmail.com";
        String subject = "[😀😀] 알림메일 입니다.";
        String body = "안녕하세요?\r\n소통해요~\r\n SMTP메일 테스트입니다.";

        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(addr);
        smm.setTo(email);
        smm.setSubject(subject);
        smm.setText(body);

        mailSender.send(smm);
    }
}
