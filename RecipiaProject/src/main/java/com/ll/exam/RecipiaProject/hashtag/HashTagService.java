package com.ll.exam.RecipiaProject.hashtag;

import com.ll.exam.RecipiaProject.post.Post;
import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class HashTagService {
    private final HashTagRepository hashTagRepository;
    private final UserRepository userRepository;

    public List<HashTag> getHashTagList(){
        return hashTagRepository.findAll();
    }



    public void createHashTag(HashTagFormDto hashTagFormDto, Principal principal){
        SiteUser user=userRepository.findByUsername("user1").orElseThrow(()->new EntityNotFoundException());
        HashTag hashTag = hashTagFormDto.createHashTag(user);
        hashTagRepository.save(hashTag);
    }


}
