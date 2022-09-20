package com.ll.exam.RecipiaProject.allergy;

import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class AllergyService {
    private final AllergyRepository allergyRepository;
    private final UserRepository userRepository;
    public void createAllergy(String allergyContent, Principal principal){


    }

    public List<Allergy> getAllergyList() { return allergyRepository.findAll();}
}
