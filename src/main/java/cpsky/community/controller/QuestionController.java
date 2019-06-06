package cpsky.community.controller;

import cpsky.community.dto.CommentCreateDto;
import cpsky.community.dto.CommentDto;
import cpsky.community.dto.QuestionDto;
import cpsky.community.service.CommentService;
import cpsky.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: sky
 * @Date: 2019/5/27 10:35
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        QuestionDto questionDto = questionService.getById(id);
        List<CommentDto> comments = commentService.listByQuestionId(id);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDto);
        model.addAttribute("comments", comments);
        return "question";
    }
}
