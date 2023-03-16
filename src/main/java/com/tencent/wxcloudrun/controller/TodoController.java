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

        log.info("<--/api/todo/list get request! userId-->{}", userId);

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
