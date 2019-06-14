package cpsky.community.dto;

import lombok.Data;

import java.util.List;

/**
 * \* Author: sky
 * \* Date: 2019/6/14
 * \* Description:
 * \
 */
@Data
public class TagDto {
    private String categoryName;
    private List<String> tags;
}