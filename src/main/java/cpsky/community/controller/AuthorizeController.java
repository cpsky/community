package cpsky.community.controller;

import cpsky.community.dto.AccessTokenDTO;
import cpsky.community.dto.GithubUser;
import cpsky.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: sky
 * @Date: 2019/5/20 14:35
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("9d63011b7811c6191bcc");
        accessTokenDTO.setClient_secret("73bb9006adcbb0eb8b955f4b8bfafca5ffda864a");
        String accessToken = githubProvider.getAccesstoken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
