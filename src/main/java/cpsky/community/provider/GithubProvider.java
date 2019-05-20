package cpsky.community.provider;

import com.alibaba.fastjson.JSON;
import cpsky.community.dto.AccessTokenDTO;
import cpsky.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: sky
 * @Date: 2019/5/20 14:43
 */
@Component
public class GithubProvider {
    public String getAccesstoken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //先把传入的对象转换为json的string类型，再把传入的数据封装为Json格式
        RequestBody body = RequestBody.create(mediaType, com.alibaba.fastjson.JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            //接受到的数据是字符串
            String[] split = s.split("&");
            String tokenstr = split[0];
            String token = tokenstr.split("=")[1];
            System.out.println(token);
            return token;
        } catch (IOException e) {
        }
        return null;
    }
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            //把json对象（string表示）转换为java对象
            GithubUser githubUser = JSON.parseObject(s, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
