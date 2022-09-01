package com.ll.exam.RecipiaProject.hashtag;

import com.ll.exam.RecipiaProject.post.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HashTagTest {

    @Autowired
    private HashTagRepository hashTagRepository;

    @BeforeEach
    public void beforeEach() throws IOException {
        clearData();
        createSample();
    }

    private void createSample() throws IOException{
        HashTag hashTag = null;
        for(int i = 1; i <= 2; i++){
            hashTag=HashTag.builder()
                    .tagContent("tagContent%s".formatted(i))
                    .tagCategory("tagCategory%s".formatted(i))
                    .tagView(1*i)
                    .build();
            hashTagRepository.save(hashTag);

        }
    }

    private void clearData(){
        hashTagRepository.deleteAll();

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void save(){
        HashTag hashTag = hashTagRepository.findByTagContent("tagContent1");
        Assertions.assertEquals("tagContent1", hashTag.getTagContent());
    }
}
