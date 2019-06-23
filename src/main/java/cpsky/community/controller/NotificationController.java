package cpsky.community.controller;

import cpsky.community.dto.NotificationDto;
import cpsky.community.enums.NotificationTypeEnum;
import cpsky.community.mapper.NotificationMapper;
import cpsky.community.model.User;
import cpsky.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * \* Author: sky
 * \* Date: 2019/6/22
 * \* Description:
 * \
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          HttpServletRequest request) {
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDto notificationDto = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDto.getType() ||
        NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDto.getType()) {
            return "redirect:/question/" + notificationDto.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}