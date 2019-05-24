package cpsky.community.dto;

import lombok.Data;

/**
 * @Author: sky
 * @Date: 2019/5/20 14:50
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;
}
