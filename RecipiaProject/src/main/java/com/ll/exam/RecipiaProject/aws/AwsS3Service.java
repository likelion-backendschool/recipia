package com.ll.exam.RecipiaProject.aws;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class AwsS3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    String imageName="";
    public String uploadImage(List<MultipartFile> multipartFiles){
//        List<String> imageNameList=new ArrayList<>();

        multipartFiles.forEach(file->{
            String imageOriName=file.getOriginalFilename();
            String ext=imageOriName.substring(imageOriName.lastIndexOf(".")+1);
            imageName= UUID.randomUUID().toString()+"."+ext;
            ObjectMetadata objectMetadata=new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            try( InputStream inputStream=file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket,imageName,inputStream,objectMetadata));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return imageName;
    }
    public void deleteImage(String fileName){
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
    public String findImage(String fileName){
        return amazonS3.getUrl(bucket,fileName).toString();
    }
}