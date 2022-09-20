package com.ll.exam.RecipiaProject.hashtag;

import com.ll.exam.RecipiaProject.post.Post;
import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
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



    public void createHashTag(String tagContent, Principal principal,Post post){
        SiteUser user=userRepository.findByUsername(principal.getName()).orElseThrow(()->new EntityNotFoundException());
        String[] tags = tagContent.split("#");
        for(String tag: tags){
            HashTag hashTag=new HashTag();
            tag = tag.trim();
            if(tag.length() == 0||tag.isEmpty() ) continue;
            hashTag.setTagContent(tag);
            hashTag.setSiteUser(user);
            hashTag.setPost(post);
            hashTagRepository.save(hashTag);
        }

    }


    public void modifyHashTag(String tagContent,Post post,Principal principal){
        deleteHashTag(post.getHashTagList());
       createHashTag(tagContent,principal,post);
    }

    @Transactional
    private void deleteHashTag(List<HashTag> hashTags) {
        for(HashTag hashTag:hashTags){
            hashTagRepository.deleteById(hashTag.getTagId());
        }
    }

}
