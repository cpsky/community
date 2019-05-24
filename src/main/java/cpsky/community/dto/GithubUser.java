package cpsky.community.dto;

import lombok.Data;

/**
 * @Author: sky
 * @Date: 2019/5/20 15:51
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
