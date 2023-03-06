package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {

    Optional<Card> getById(Integer id);

    List<Card> listByUserId(Integer userId);


    void save(Card card);

    void update(Card card);
}
