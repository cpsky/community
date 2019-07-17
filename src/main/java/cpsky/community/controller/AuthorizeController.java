package cpsky.community.controller;

import cpsky.community.dto.AccessTokenDTO;
import cpsky.community.dto.GithubUser;
import cpsky.community.mapper.UserMapper;
import cpsky.community.model.User;
import cpsky.community.provider.GithubProvider;
import cpsky.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: sky
 * @Date: 2019/5/20 14:35
 * 获得github授权
 */
@Controller
@Slf4j
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        //accessTokenDTO.setRedirect_url("http://localhost:8787/callback");
        String accessToken = githubProvider.getAccesstoken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        //判断是否授权成功
        if (githubUser != null && githubUser.getName() != null) {
            String token = UUID.randomUUID().toString();
            /*
            **自己想的方式
            User user = userMapper.findByAcoountId(String.valueOf(githubUser.getId()));
            //判断是否是已存在该user
            if (user != null) {
                user.setToken(token);
                user.setGmtModified(System.currentTimeMillis());
                userMapper.updateLoginUser(user);
                Cookie cookie = new Cookie("token", token);
                response.addCookie(cookie);
            } else {
                user = new User();
                user.setToken(token);
                user.setName(githubUser.getName());
                //String.valueof方法比 toString 方法好 不会返回空指针异常，若为null则返回字符串"null"
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(githubUser.getAvatarUrl());
                userService
                userMapper.insert(user);
                //登录成功，写cookie和session
                Cookie cookie = new Cookie("token", token);
                response.addCookie(cookie);
                //request.getSession().setAttribute("githubUser", githubUser);
            }*/
            //讲解方式
            User user = new User();
            user.setToken(token);
            user.setName(githubUser.getName());
            //String.valueof方法比 toString 方法好 不会返回空指针异常，若为null则返回字符串"null"
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            log.error("callback get github error ,{}",githubUser);
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
