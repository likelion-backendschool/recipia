package com.ll.exam.RecipiaProject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

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

    public int sendEmail(String email) {
        Random r = new Random();
        int num = r.nextInt(999999);

        String addr = "gi020477@gmail.com";
        String subject = "[Recipia🔑] 임시번호 발송 메일입니다.";
        String body = "임시번호: %d".formatted(num);

        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(addr);
        smm.setTo(email);
        smm.setSubject(subject);
        smm.setText(body);
        mailSender.send(smm);

        return num;
    }

    public void updatePw(String email, String password) {
        SiteUser updateUser = userRepository.findByEmail(email).orElse(null);
        updateUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(updateUser);
    }
}
