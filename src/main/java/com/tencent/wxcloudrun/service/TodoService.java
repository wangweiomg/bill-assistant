package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Todo;

import java.util.List;

public interface TodoService {


    void batchInsert(List<Todo> todos);
}
