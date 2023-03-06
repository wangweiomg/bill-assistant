package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final UserMapper userMapper;

    public UserServiceImpl(@Autowired UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public void upsert(User user) {

        userMapper.upsert(user);

    }

    @Override
    public Optional<User> getByOpenId(String openId) {
        return Optional.ofNullable(userMapper.getByOpenId(openId));
    }

    @Override
    public Optional<User> getById(Integer id) {
        return Optional.ofNullable(userMapper.getById(id));
    }
}
