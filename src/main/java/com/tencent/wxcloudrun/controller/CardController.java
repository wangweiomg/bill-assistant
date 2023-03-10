package com.tencent.wxcloudrun.controller;

import java.util.List;
import java.util.Optional;

import com.tencent.wxcloudrun.constants.WxRequestHeaderNamesConstant;
import com.tencent.wxcloudrun.dto.CardRequest;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Card;
import com.tencent.wxcloudrun.service.CardService;

@RestController
public class CardController {

    final CardService cardService;
    final UserService userService;
    final Logger logger;

    public CardController(@Autowired CardService cardService, @Autowired UserService userService) {

        this.cardService = cardService;
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(CardController.class);
    }
    

    @GetMapping("/api/card")
    ApiResponse get() {

        logger.info("/api/card get request");
        Optional<Card> card = cardService.getById(1);


        Card c = new Card();
        if (card.isPresent()) {
            c = card.get();
        }
        return ApiResponse.ok(c);
        
    }

    @GetMapping("/api/card/list")
    ApiResponse list(@RequestParam Integer userId) {
        logger.debug("/api/card/list get request, userId-->{}", userId);

        List<Card> list = cardService.listByUserId(userId);
        return ApiResponse.ok(list);

    }

    @PostMapping("/api/card/upsert")
    ApiResponse upsert(@RequestBody CardRequest request, @RequestHeader HttpHeaders headers) {
        logger.info("/api/card/upsert post request");
        logger.debug("/api/card/upsert post request, param-->{}, headers-->{}", request, headers);

        String openId = headers.getFirst(WxRequestHeaderNamesConstant.OPEN_ID);

        Card card = new Card();
        card.setName(request.getName());
        card.setRemark(request.getRemark());
        card.setCardLimit(request.getCardLimit());
        card.setBillDay(request.getBillDay());
        card.setRepayDayType(request.getRepayDayType());
        card.setRepayDayNum(request.getRepayDayNum());

        // card 设置默认值
        // openId 换userId
        Optional<User> user = userService.getByOpenId(openId);

        if (!user.isPresent()) {
            logger.error("<--invalid openId! user not exists--> openId-->{}", openId);
            return ApiResponse.error("invalid open_id !");
        }

        card.setUserId(user.get().getId());
        card.setCreatedBy(user.get().getId());

        if (request.getCardId() != null) {
            card.setId(request.getCardId());
            cardService.update(card);
        } else {
            cardService.save(card);
        }

        return ApiResponse.ok();
    }

    @PostMapping(value = "/api/card/remove/{id}")
    ApiResponse delete(@PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        logger.info("<-- /api/card/remove post request-->");

        String openId = headers.getFirst(WxRequestHeaderNamesConstant.OPEN_ID);

        Optional<User> user = userService.getByOpenId(openId);

        user.ifPresent(value -> cardService.remove(id));

        return ApiResponse.ok();

    }

}
