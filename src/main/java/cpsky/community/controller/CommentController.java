package cpsky.community.controller;

import cpsky.community.dto.CommentCreateDto;
import cpsky.community.dto.CommentDto;
import cpsky.community.dto.ResultDto;
import cpsky.community.enums.CommentTypeEnum;
import cpsky.community.exception.CustomizErrorCode;
import cpsky.community.model.Comment;
import cpsky.community.model.User;
import cpsky.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        if (user == null) {
            return ResultDto.errof(CustomizErrorCode.NO_LOGIN);
        }
        if (commentCreateDto == null || StringUtils.isBlank(commentCreateDto.getContent())) {
            return ResultDto.errof(CustomizErrorCode.CONTENT_IS_EMPTY);
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
    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDto<List<CommentDto>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDto> commentDtos = commentService.listByTargetId(id, CommentTypeEnum.Comment);
        return ResultDto.okOf(commentDtos);
    }
}
