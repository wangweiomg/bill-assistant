package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.User;

public interface UserService {

    void upsert(User user);
}
