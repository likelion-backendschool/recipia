package com.ll.exam.RecipiaProject.allergy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.io.IOException;

@SpringBootTest
public class AllergyTest {
    @Autowired
    private AllergyRepository allergyRepository;

    @BeforeEach
    public void beforeEach() throws IOException {
        clearData();
        createSample();
    }

    private void clearData() {
        allergyRepository.deleteAll();;
        allergyRepository.truncate();
    }

    private void createSample() {
        Allergy allergy = null;
        for(int i = 1; i <=6; i++){
            allergy=Allergy.builder()
                    .allergyContent("allergyContent%s".formatted(i))
                    .allergyCategory("allergyCategory%s".formatted(i))
                    .build();
            allergyRepository.save(allergy);
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void save(){
        Allergy allergy = allergyRepository.findByAllergyContent("allergyContent1");
        Assertions.assertEquals("allergyContent1", allergy.getAllergyContent());
    }
}

