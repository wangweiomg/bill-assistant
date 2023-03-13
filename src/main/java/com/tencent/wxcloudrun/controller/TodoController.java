package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.TodoRequest;
import com.tencent.wxcloudrun.model.Card;
import com.tencent.wxcloudrun.model.Todo;
import com.tencent.wxcloudrun.service.CardService;
import com.tencent.wxcloudrun.service.TodoService;
import com.tencent.wxcloudrun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class TodoController {

    final UserService userService;
    final CardService cardService;
    final TodoService todoService;

    public TodoController (@Autowired UserService userService, @Autowired CardService cardService, @Autowired TodoService todoService) {
        this.userService = userService;
        this.cardService = cardService;
        this.todoService = todoService;
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
            if (todo.getDeadline().toLocalDate().isBefore(LocalDate.now())) {
                todo.setStatus(1);
            }
            return todo;
        }).sorted(Comparator.comparing(Todo::getDeadline)).collect(Collectors.toList());


        List<Todo> list = todoService.listByUserId(userId);

        return ApiResponse.ok(list);


    }

    @PostMapping(value = "/api/todo/update")
    ApiResponse update(@RequestBody List<TodoRequest> list, @RequestParam Integer userId) {

        log.info("<--/api/todo/update-->userId-->{}, list-->{}", userId, list);

        if (CollectionUtils.isEmpty(list)) {
            log.warn("<--no todos need to update, list is empty!-->");
            return ApiResponse.ok();
        }


        List<Todo> todos = list.stream().map(i -> {
            val todo = new Todo();
            todo.setId(i.getId());
            todo.setStatus(i.getStatus());
            if (i.getChecked()) {
                todo.setCompleteDate(LocalDateTime.now());
            }
            todo.setUpdatedBy(userId);
            todo.setUpdatedAt(LocalDateTime.now());
            return todo;

        }).collect(Collectors.toList());

        todoService.batchUpdate(todos);

        return ApiResponse.ok();

    }


}
