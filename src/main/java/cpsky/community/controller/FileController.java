package cpsky.community.controller;

import cpsky.community.dto.FileDto;
import cpsky.community.provider.AliCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * \* Author: sky
 * \* Date: 2019/7/2
 * \* Description:
 * \
 */
@Controller
public class FileController {
    @Autowired
    AliCloudProvider aliCloudProvider;

    @ResponseBody
    @PostMapping("/file/upload")
    public FileDto upload (HttpServletRequest request) {
        String url = "";
        MultipartHttpServletRequest multiparRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multiparRequest.getFile("editormd-image-file");
        try {
            url = aliCloudProvider.upload(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDto fileDto = new FileDto();
        fileDto.setSuccess(1);
        //fileDto.setUrl("https://cxlsky.oss-cn-beijing.aliyuncs.com/community/" + file.getOriginalFilename());
        fileDto.setUrl(url);
        return fileDto;
    }
}