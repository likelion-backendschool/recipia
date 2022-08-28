package com.ll.exam.RecipiaProject.post.postImg;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
        FileOutputStream fos=new FileOutputStream(fileUploadUrl);
        fos.write(file.getBytes());
        fos.close();
        return savedFileName;
    }

}
