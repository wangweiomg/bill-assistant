package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.WxRequestHeaderNamesConstant;
import com.tencent.wxcloudrun.dto.TodoResponse;
import com.tencent.wxcloudrun.model.Card;
import com.tencent.wxcloudrun.model.Todo;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CardService;
import com.tencent.wxcloudrun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class TodoController {

    final UserService userService;
    final CardService cardService;

    public TodoController (@Autowired UserService userService, @Autowired CardService cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }

    @GetMapping(value = "/api/todo/list")
    ApiResponse list(@RequestHeader HttpHeaders headers) {

        log.info("<--/api/todo/list get request-->");

        String openId = headers.getFirst(WxRequestHeaderNamesConstant.OPEN_ID);

        Optional<User> userOptional = userService.getByOpenId(openId);

        if (!userOptional.isPresent()) {
            return ApiResponse.error("<--invalid user!");
        }

        User user = userOptional.get();

        List<Card> cards = cardService.listByUserId(user.getId());

        List<Todo> cardTodos = cards.stream().map(i -> {
            val todo = new Todo();
            todo.setName(i.getName());

            LocalDate now = LocalDate.now();
            boolean isBilled = now.getDayOfMonth() > i.getBillDay();

            LocalDate start = now;
            LocalDate end = now;
            if (isBilled) {
                start = now.withDayOfMonth(i.getBillDay());

                if (i.getRepayDayType() == 1) {
                    end = i.getRepayDayNum() > i.getBillDay() ? now.withDayOfMonth(i.getRepayDayNum()) : now.withDayOfMonth(i.getRepayDayNum()).plusMonths(1);
                } else {
                    end = start.plusDays(i.getRepayDayNum());
                }

            } else {

                start = now.withDayOfMonth(i.getBillDay()).plusMonths(-1);

                if (i.getRepayDayType() == 1) {

                } else {

                }
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            todo.setPeriod(formatter.format(start) + " - " + formatter.format(end));

            todo.setStatus(0);

            return todo;
        }).collect(Collectors.toList());


        return ApiResponse.ok(cardTodos);



    }

}
