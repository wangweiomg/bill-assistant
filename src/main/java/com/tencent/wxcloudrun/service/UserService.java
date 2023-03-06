package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.User;

import java.util.Optional;

public interface UserService {

    void upsert(User user);

    Optional<User> getByOpenId(String openId);

    Optional<User> getById(Integer id);
}
