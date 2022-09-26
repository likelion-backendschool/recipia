package com.ll.exam.RecipiaProject.allergy;

import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Transactional
@RequiredArgsConstructor
@Service
public class AllergyService {
    private final AllergyRepository allergyRepository;
    private final UserRepository userRepository;
    public void createAllergy(String allergyContent, Principal principal){
        SiteUser user=userRepository.findByUsername(principal.getName()).orElseThrow(()->new EntityNotFoundException());
        String[] allergyList = allergyContent.split("#");
        for(String all: allergyList){
            Allergy allergy = new Allergy();
            all = all.trim();
            if(all.length()==0||all.isEmpty()) continue;
            allergy.setAllergyContent(all);
            allergy.setSiteUser(user);
            allergyRepository.save(allergy);
        }

    }

//    public void modifyAllergy(String allergyContent,Allergy allergy, Principal principal){
//        deleteAllergy(allergy.);
//    }

//    private void deleteAllergy(List<Allergy> allergies){
//        for(Allergy allergy: allergies){
//            allergyRepository.deleteById(allergy.getAllergyId());
//        }
//    }

    public List<Allergy> getAllergyList() { return allergyRepository.findAll();}

    public void deleteAllergy(int allergyId, Principal principal) {
        allergyRepository.deleteById(allergyId);
    }
}
