package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.TodoMapper;
import com.tencent.wxcloudrun.model.Todo;
import com.tencent.wxcloudrun.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    final TodoMapper todoMapper;
    public TodoServiceImpl(@Autowired TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    @Override
    public void batchInsert(List<Todo> todos) {

        todoMapper.batchInsert(todos);

    }

    @Override
    public void batchUpdate(List<Todo> todos) {
        todoMapper.batchUpdate(todos);
    }

    @Override
    public void expire() {
        todoMapper.expire();
    }

    @Override
    public List<Todo> listByUserId(Integer userId) {
        return todoMapper.listByUserId(userId);
    }

}
