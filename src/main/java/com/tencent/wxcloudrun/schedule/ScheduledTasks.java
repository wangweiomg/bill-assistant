package com.tencent.wxcloudrun.schedule;

import com.tencent.wxcloudrun.model.Card;
import com.tencent.wxcloudrun.model.Todo;
import com.tencent.wxcloudrun.service.CardService;
import com.tencent.wxcloudrun.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScheduledTasks {

    final TodoService todoService;
    final CardService cardService;

    public ScheduledTasks (@Autowired TodoService todoService, @Autowired CardService cardService) {
        this.todoService = todoService;
        this.cardService = cardService;
    }


    /**
     * 每天凌晨生成当天新出的待办
     */
    @Scheduled(cron = "0 10 0 * * ?")
    public void generateTodos() {

        // 查出所有的card, 当天出账单就生成一条待办
        int billDay = LocalDate.now().getDayOfMonth();
        List<Card> list = cardService.listByBillDay(billDay);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        List<Todo> todos = list.stream().map(i -> {

            LocalDate billDate = today.withDayOfMonth(i.getBillDay());
            LocalDate repayDate = today;
            if (i.getRepayDayType() == 1) {
                repayDate = i.getRepayDayNum() > i.getBillDay() ? today.withDayOfMonth(i.getRepayDayNum()) : today.withDayOfMonth(i.getRepayDayNum()).plusMonths(1);
            } else if (i.getRepayDayType() == 2) {
                repayDate = billDate.plusDays(i.getRepayDayNum());
            }


            val todo = new Todo();
            todo.setUserId(i.getUserId());
            todo.setName(i.getName() + "， 还款日:" + formatter.format(repayDate));
            todo.setType(1);
            todo.setStatus(0);
            todo.setDeadline(repayDate.plusDays(1).atStartOfDay().plusSeconds(-1));
            todo.setCreatedBy(1);
            return todo;

        }).collect(Collectors.toList());


        todoService.batchInsert(todos);

    }

}
