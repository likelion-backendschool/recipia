package com.ll.exam.RecipiaProject.aws;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ll.exam.RecipiaProject.post.Post;
import com.ll.exam.RecipiaProject.post.postImg.PostImg;
import com.ll.exam.RecipiaProject.post.postImg.PostImgRepository;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import java.util.UUID;
@Service
@Transactional
@RequiredArgsConstructor
public class AwsS3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final PostImgRepository postImgRepository;
    public void createPostImg(PostImg postImg, MultipartFile file) throws IOException {
            String imageOriName=file.getOriginalFilename();
            String ext=imageOriName.substring(imageOriName.lastIndexOf(".")+1);
            String savedFileName= UUID.randomUUID().toString()+"."+ext;
            String imgUrl="";

            BufferedImage bi= ImageIO.read(file.getInputStream());

            try {
                bi=resizeImage(bi,450,450);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi,ext,baos);
            baos.flush();
            MultipartFile multipartFileRe=new MockMultipartFile(savedFileName,baos.toByteArray());
            ObjectMetadata objectMetadata=new ObjectMetadata();
            objectMetadata.setContentLength(multipartFileRe.getSize());
            objectMetadata.setContentType(multipartFileRe.getContentType());

            try( InputStream inputStream=multipartFileRe.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket,savedFileName,inputStream,objectMetadata));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imgUrl=amazonS3.getUrl(bucket,savedFileName).toString();

            postImg.updatePostImg(imageOriName,savedFileName,imgUrl);
            postImgRepository.save(postImg);
    }
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_EXACT, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
    }

    public void deletePostImgList(Post post){
        List<PostImg> postImgList=post.getPostImgList();
        for(int i=0;i<postImgList.size();i++){
            PostImg postImg=postImgList.get(i);
            amazonS3.deleteObject(new DeleteObjectRequest(bucket,postImg.getImgName()));
            postImgList.remove(postImg);
            postImgRepository.deleteById(postImg.getId());
        }
    }
    public void deletePostImg(int postImgId) {
        postImgRepository.deleteById(postImgId);
    }

    public String findImage(String fileName){
        return amazonS3.getUrl(bucket,fileName).toString();
    }
}