package cpsky.community.dto;

import cpsky.community.model.User;
import lombok.Data;

/**
 * @Author: sky
 * @Date: 2019/5/24 14:30
 */
@Data
public class QuestionDto {
    private int id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
