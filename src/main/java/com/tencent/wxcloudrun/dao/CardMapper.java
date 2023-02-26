package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Card;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CardMapper {

    Card getById(@Param("id") Integer id);

    List<Card> listByUserId(@Param("userId") Integer userId);
}
