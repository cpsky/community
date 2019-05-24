package cpsky.community.controller;

import cpsky.community.Service.QuestionService;
import cpsky.community.dto.QuestionDto;
import cpsky.community.mapper.UserMapper;
import cpsky.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        //判断浏览器是否禁用cookie
        //如果禁用cookie,会把jsessionid写在响应头中的set-cookie中
        Boolean flag = request.isRequestedSessionIdFromCookie();
        request.getSession().setAttribute("flag", flag);
        Cookie[] cookies = request.getCookies();
        List<QuestionDto> questionList = questionService.list();
        model.addAttribute("questions", questionList);
        if (cookies == null) {
            return "index";
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token") && cookie.getValue() != null) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        //记录登录状态
                        request.getSession().setAttribute("user_status", 0);
                    }
                    break;
                } else {
                    request.getSession().setAttribute("user_status", -1);
                }
            }
            return "index";
        }
    }
    //退出登录
    @GetMapping("/exit")
    public String exit(HttpServletRequest request,
                       HttpServletResponse response){
        Cookie cookie = new Cookie("token",null);
        request.getSession().setAttribute("user", null);//cookie名字要相同
        cookie.setMaxAge(0); //
        cookie.setPath(request.getContextPath());  // 相同路径
        response.addCookie(cookie);
        return "redirect:/";
    }
}
