package com.ll.exam.RecipiaProject.post.postImg;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
@Transactional
@Service
public class PostImgFileService {
    public String uploadFile(String originImgName,MultipartFile file,String postImgLocation) throws Exception{
        UUID uuid=UUID.randomUUID();
        String extension=originImgName.substring(originImgName.lastIndexOf("."));
        String savedFileName=uuid.toString()+extension;
        String fileUploadUrl=postImgLocation+savedFileName;

        BufferedImage bi=ImageIO.read(file.getInputStream());
        bi=resizeImage(bi,450,450);
        ImageIO.write(bi,"jpg",new File(fileUploadUrl));
        return savedFileName;
    }

    //이미지 리사이징 해주는 메서드
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_EXACT, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
    }

}
