package cpsky.community.provider;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import cpsky.community.dto.KeyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * \* Author: sky
 * \* Date: 2019/7/2
 * \* Description:
 * \
 */
@Component
public class AliCloudProvider {

    @Autowired
    private XMLProvider xmlProvider;

    public String upload(InputStream inputStream, String fileName) {
        URL url = null;
        try {
            String resFilename;
            resFilename = "community/" + fileName;
            KeyDto keyDto = xmlProvider.getUpLoadKey();
            String endpoint = "http://oss-cn-beijing.aliyuncs.com";
            String accessKeyId = keyDto.getKeyid();
            String accessKeySecret = keyDto.getKeysecert();
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult res = ossClient.putObject("cxlsky", resFilename, inputStream);
            //生成文件url

            Date expiration = new Date(new Date().getTime() + 1000*3600*24*365*10);
            url = ossClient.generatePresignedUrl("cxlsky", resFilename, expiration);

        } catch (OSSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "https://cxlsky.oss-cn-beijing.aliyuncs.com" + url.getFile();
    }
}