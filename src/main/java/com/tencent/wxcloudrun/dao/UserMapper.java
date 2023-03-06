package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    void upsert(User user);

    User getByOpenId(@Param("openId") String openId);

    User getById(@Param("id") Integer id);
}
