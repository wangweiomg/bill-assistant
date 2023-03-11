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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
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
    ApiResponse list(@RequestParam Integer userId) {

        log.info("<--/api/todo/list get request!");


        List<Card> cards = cardService.listByUserId(userId);

        List<Todo> cardTodos = cards.stream().filter(i -> i.getBillDay() != null && i.getRepayDayNum() != null).map(i -> {


            LocalDate now = LocalDate.now();
            boolean isBilled = now.getDayOfMonth() > i.getBillDay();

            LocalDate billDate = now;
            LocalDate repayDate = now;
            if (isBilled) {
                billDate = now.withDayOfMonth(i.getBillDay());

                if (i.getRepayDayType() == 1) {
                    repayDate = i.getRepayDayNum() > i.getBillDay() ? now.withDayOfMonth(i.getRepayDayNum()) : now.withDayOfMonth(i.getRepayDayNum()).plusMonths(1);
                } else {
                    repayDate = billDate.plusDays(i.getRepayDayNum());
                }

            } else {

                billDate = now.withDayOfMonth(i.getBillDay()).plusMonths(-1);

                if (i.getRepayDayType() == 1) {

                    repayDate = now.withDayOfMonth(i.getRepayDayNum()).plusMonths(-1).isBefore(billDate) ? now.withDayOfMonth(i.getRepayDayNum()) : now.withDayOfMonth(i.getRepayDayNum()).plusMonths(-1);

                } else {

                    repayDate = billDate.plusDays(i.getRepayDayNum());
                }
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

            val todo = new Todo();
            todo.setType(1);
            todo.setName(i.getName() + "        还款日:" + formatter.format(repayDate));
            todo.setDeadline(repayDate.plusDays(1).atStartOfDay().plusSeconds(-1));
            todo.setStatus(0);
            todo.setId(i.getId());

            return todo;
        }).sorted(Comparator.comparingInt(o -> o.getDeadline().getSecond())).collect(Collectors.toList());


        return ApiResponse.ok(cardTodos);



    }


}
