package cpsky.community.controller;

import cpsky.community.mapper.QuestionMapper;
import cpsky.community.mapper.UserMapper;
import cpsky.community.model.Question;
import cpsky.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: sky
 * @Date: 2019/5/23 11:13
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "tag") String tag,
            HttpServletRequest request,
            Model model) {
        User user = null;
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if(title == null || "".equals(title.trim())){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(description == null || "".equals(description.trim())){
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if(tag == null || "".equals(tag.trim())){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        questionMapper.create(question);
        return "redirect:/";
    }
}
