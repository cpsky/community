package cpsky.community.dto;


import cpsky.community.model.User;
import lombok.Data;

/**
 * \* Author: sky
 * \* Date: 2019/6/21
 * \* Description:
 * \
 */
@Data
public class NotificationDto {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String outerTitle;
    private String notifierName;
    private Long outerid;
    private String typeName;
    private Integer type;
}