package com.tencent.wxcloudrun.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Card;
import com.tencent.wxcloudrun.service.CardService;

@RestController
public class CardController {

    final CardService cardService;
    final Logger logger;

    public CardController(@Autowired CardService cardService) {

        this.cardService = cardService;
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
    ApiResponse list() {
        logger.info("/api/card/list get request");
        List<Card> list = cardService.listByUserId(1);
        return ApiResponse.ok(list);

    }

}
