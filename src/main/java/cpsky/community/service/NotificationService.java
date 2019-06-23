package cpsky.community.service;

import com.sun.javaws.exceptions.ErrorCodeResponseException;
import cpsky.community.dto.NotificationDto;
import cpsky.community.dto.PaginationDTO;
import cpsky.community.enums.NotificationStatusEnum;
import cpsky.community.enums.NotificationTypeEnum;
import cpsky.community.exception.CustomizErrorCode;
import cpsky.community.exception.CustomizeException;
import cpsky.community.exception.ICustomizErrorCode;
import cpsky.community.mapper.NotificationMapper;
import cpsky.community.mapper.UserMapper;
import cpsky.community.model.Notification;
import cpsky.community.model.NotificationExample;
import cpsky.community.model.User;
import cpsky.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * \* Author: sky
 * \* Date: 2019/6/21
 * \* Description:
 * \
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        Integer count = (int)notificationMapper.countByExample(example);
        if (page < 1) {
            page = 1;
        }
        if (page > count) {
            if(count % size == 0)
                page = count / size;
            else {
                page = count / size + 1;
            }
        }
        Integer offset = size * (page - 1);
        PaginationDTO<NotificationDto> paginationDTO = new PaginationDTO();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        notificationExample.setOrderByClause("GMT_CREATE DESC");
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        if (notificationList.size() == 0) {
            return paginationDTO;
        }
        for (Notification notification : notificationList) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification, notificationDto);
            notificationDto.setTypeName(NotificationTypeEnum.nameofType(notification.getType()));
            notificationDtoList.add(notificationDto);
        }
        paginationDTO.setData(notificationDtoList);
        Integer totalCount = (int)notificationMapper.countByExample(example);
        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }

    public Long unreadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        long count = notificationMapper.countByExample(notificationExample);
        return count;
    }

    public NotificationDto read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getId())) {
            throw new CustomizeException(CustomizErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andIdEqualTo(id);
        notificationMapper.updateByExampleSelective(notification, notificationExample);
        NotificationDto notificationDto = new NotificationDto();
        BeanUtils.copyProperties(notification, notificationDto);
        notificationDto.setTypeName(NotificationTypeEnum.nameofType(notification.getType()));
        return notificationDto;
    }
}