package cpsky.community.dto;

import lombok.Data;

/**
 * \* Author: sky
 * \* Date: 2019/7/6
 * \* Description:
 * \
 */
@Data
public class QuestionQueryDto {
    private String search;
    private Integer page;
    private Integer size;
}