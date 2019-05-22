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
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Update("update user set name = #{name},token =#{token},gmt_modified = #{gmtModified} where id = #{id}")
    int updateLoginUser(User user);

    @Select("select * from user where account_id = #{accountId}")
    User findByAcoountId(@Param("accountId") String accountId);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
