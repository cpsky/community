package cpsky.community.controller;

import cpsky.community.mapper.UserMapper;
import cpsky.community.model.User;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        //判断浏览器是否禁用cookie
        //如果禁用cookie,会把jsessionid写在响应头中的set-cookie中
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "index";
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token") && cookie.getValue() != null) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                } else {
                    request.getSession().setAttribute("user", null);
                }
            }
            return "index";
        }
    }
    @GetMapping("/exit")
    public String exit(HttpServletRequest request,
                       HttpServletResponse response){
        Cookie cookie = new Cookie("token",null);//cookie名字要相同
        cookie.setMaxAge(0); //
        cookie.setPath(request.getContextPath());  // 相同路径
        response.addCookie(cookie);
        return "redirect:/";
    }
}
