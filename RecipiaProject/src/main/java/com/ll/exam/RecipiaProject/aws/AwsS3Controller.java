package com.ll.exam.RecipiaProject.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/s3")
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;
    private static String fileName;
    @GetMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> uploadFile(@RequestPart List<MultipartFile>multipartFiles){

        fileName=awsS3Service.uploadImage(multipartFiles);
        return new ResponseEntity<>("생성성공했구나", HttpStatus.OK);
    }
    @GetMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> deleteFile(){
        awsS3Service.deleteImage(fileName);
        return new ResponseEntity<>("삭제성공했구나", HttpStatus.OK);
    }
    @GetMapping("/find")
    public String findFile(Model model){
        String path=awsS3Service.findImage(fileName);
        model.addAttribute("path",path);
        return  "test";
    }
}