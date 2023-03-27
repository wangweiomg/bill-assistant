package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.CardMapper;
import com.tencent.wxcloudrun.model.Card;
import com.tencent.wxcloudrun.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
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

    @Override
    public List<Card> listAll() {

        return cardMapper.listAll();
    }

    @Override
    public List<Card> listByBillDay(Integer billDay) {
        return cardMapper.listByBillDay(billDay);
    }

    @Override
    public void initSample(Integer userId) {
        // 生成 5 条测试卡


        Card card1 = new Card();
        Card card2 = new Card();
        Card card3 = new Card();
        Card card4 = new Card();
        Card card5 = new Card();

        card1.setUserId(userId);
        card2.setUserId(userId);
        card3.setUserId(userId);
        card4.setUserId(userId);
        card5.setUserId(userId);

        card1.setName("工商银行-测试");
        card2.setName("建设银行-测试");
        card3.setName("农业银行-测试");
        card4.setName("浦发银行-测试");
        card5.setName("招商银行-测试");

        card1.setCardType(1);
        card2.setCardType(1);
        card3.setCardType(1);
        card4.setCardType(1);
        card5.setCardType(1);

        card1.setCardLimit(BigDecimal.valueOf(60000L));
        card2.setCardLimit(BigDecimal.valueOf(45000L));
        card3.setCardLimit(BigDecimal.valueOf(30000L));
        card4.setCardLimit(BigDecimal.valueOf(200000L));
        card5.setCardLimit(BigDecimal.valueOf(68000L));


        card1.setBillDay(16);
        card1.setRepayDayType(1);
        card1.setRepayDayNum(10);

        card2.setBillDay(13);
        card2.setRepayDayType(1);
        card2.setRepayDayNum(2);

        card3.setBillDay(27);
        card3.setRepayDayType(2);
        card3.setRepayDayNum(25);

        card4.setBillDay(21);
        card4.setRepayDayType(2);
        card4.setRepayDayNum(20);

        card5.setBillDay(5);
        card5.setRepayDayType(1);
        card5.setRepayDayNum(23);


        save(card1);
        save(card2);
        save(card3);
        save(card4);
        save(card5);


    }

}
