package cpsky.community.model;

import lombok.Data;

/**
 * @Author: sky
 * @Date: 2019/5/21 16:07
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
