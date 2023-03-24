package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.WxRequestHeaderNamesConstant;
import com.tencent.wxcloudrun.dto.TodoRequest;
import com.tencent.wxcloudrun.model.Todo;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CardService;
import com.tencent.wxcloudrun.service.TodoService;
import com.tencent.wxcloudrun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    /**
     * 这里由于是首页，不容易获取到userId,所以从headers拿openId，然后换取userId
     * @param headers
     * @return
     */
    @GetMapping(value = "/api/todo/list")
    ApiResponse list(@RequestHeader HttpHeaders headers) {


        String openId = headers.getFirst(WxRequestHeaderNamesConstant.OPEN_ID);

        log.info("<--/api/todo/list get request! openId-->{}", openId);

        Optional<User> userOptional = userService.getByOpenId(openId);
        return userOptional.map(user -> ApiResponse.ok(todoService.listByUserId(user.getId()))).orElseGet(ApiResponse::ok);


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
