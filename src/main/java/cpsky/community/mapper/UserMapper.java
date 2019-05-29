package cpsky.community.mapper;

import cpsky.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @Author: sky
 * @Date: 2019/5/21 16:02
 */
@Component
@Mapper
public interface UserMapper {


    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer creator);

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Update("update user set name = #{name},token =#{token},gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id = #{id}")
    int update(User user);

    @Select("select * from user where account_id = #{accountId}")
    User findByAcoountId(@Param("accountId") String accountId);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
