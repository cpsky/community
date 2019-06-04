package cpsky.community.dto;

import lombok.Data;

/**
 * @Author: sky
 * @Date: 2019/6/1 16:44
 */
@Data
public class CommentDto {
    private Long parentId;
    private String content;
    private Integer type;
}
