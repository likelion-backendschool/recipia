package com.ll.exam.RecipiaProject.hashtag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HashTagService {
    private final HashTagRepository hashTagRepository;

    public List<HashTag> getHashTagList(){
        return hashTagRepository.findAll();
    }

//    @Transactional
//    public Long save(final HashTag params){
//        HashTag entity = hashTagRepository.save(params.toEntity());
//        return entity.getTagId();
//    }

    @Transactional
    public Page<HashTag> searchResult(String tag, Pageable pageable){
        Page<HashTag> hashtagList = hashTagRepository.searchResult(tag, pageable);

        hashtagList.forEach(hashTag -> {

        });
        return hashtagList;
    }
}
