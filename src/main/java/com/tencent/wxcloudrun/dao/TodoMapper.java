package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Todo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TodoMapper {

    Todo getById(@Param("id") Integer id);

    List<Todo> listByUserId(@Param("userId") Integer userId);

    void save(Todo todo);

    void update(Todo todo);

    void remove(Integer id);

    void batchInsert(@Param("list") List<Todo> todos);

    void batchUpdate(@Param("list") List<Todo> todos);

    void expire();

}
