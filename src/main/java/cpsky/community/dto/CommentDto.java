package cpsky.community.dto;

import cpsky.community.model.User;
import lombok.Data;

/**
 * @Author: sky
 * @Date: 2019/6/6 13:17
 */
@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
    private Integer commentCount;
}
