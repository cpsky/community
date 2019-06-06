package cpsky.community.controller;

import cpsky.community.dto.CommentCreateDto;
import cpsky.community.dto.ResultDto;
import cpsky.community.exception.CustomizErrorCode;
import cpsky.community.model.Comment;
import cpsky.community.model.User;
import cpsky.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: sky
 * @Date: 2019/6/1 16:33
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    //requestbody将字符串反序列化为对象
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            return ResultDto.errof(CustomizErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDto.okOf();
    }
}
