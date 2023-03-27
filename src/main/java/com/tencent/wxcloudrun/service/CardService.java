package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {

    Optional<Card> getById(Integer id);

    List<Card> listByUserId(Integer userId);


    void save(Card card);

    void update(Card card);

    void remove(Integer id);

    List<Card> listAll();

    List<Card> listByBillDay(Integer billDay);

    void initSample(Integer userId);
}
