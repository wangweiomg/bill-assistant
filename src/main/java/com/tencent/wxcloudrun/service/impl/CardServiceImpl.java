package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.CardMapper;
import com.tencent.wxcloudrun.model.Card;
import com.tencent.wxcloudrun.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService{

    final CardMapper cardMapper;

    public CardServiceImpl(@Autowired CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }


    @Override
    public Optional<Card> getById(Integer id) {

        return Optional.ofNullable(cardMapper.getById(id));
    }

    @Override
    public List<Card> listByUserId(Integer userId) {
        return cardMapper.listByUserId(userId);
    }

    @Override
    public void save(Card card) {

        cardMapper.save(card);
    }

    @Override
    public void update(Card card) {
        cardMapper.update(card);
    }

    @Override
    public void remove(Integer id) {

        cardMapper.remove(id);

    }

}
