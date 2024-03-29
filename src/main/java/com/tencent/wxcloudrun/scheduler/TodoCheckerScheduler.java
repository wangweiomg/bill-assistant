package com.tencent.wxcloudrun.scheduler;

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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 每天凌晨定时跑批
 * 生成新的todo、过期改状态
 *
 * Author: Tristan
 */
@Slf4j
@Component
public class TodoCheckerScheduler {

    final TodoService todoService;
    final CardService cardService;

    public TodoCheckerScheduler(@Autowired TodoService todoService, @Autowired CardService cardService) {
        this.todoService = todoService;
        this.cardService = cardService;
    }


    /**
     * 每天凌晨生成当天新出的待办
     * TODO 要避免分布式问题, 计划用ehcache
     */
    @Scheduled(cron = "0 10 0 * * ?")
    public void generateTodos() {


        LocalDate today = LocalDate.now();

        log.info("<--start generating {} todos!-->", today);

        // 查出所有的card, 当天出账单就生成一条待办
        int billDay = today.plusDays(-1).getDayOfMonth();
        List<Card> list = cardService.listByBillDay(billDay);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

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

        log.info("<--end generating todos!-->");

    }

    /**
     * 把过期的todos修改为过期
     */
    @Scheduled(cron = "0 15 0 * * ?")
    public void updateTodos() {
        // 把过期的todos修改状态为过期
        todoService.expire();

    }

}
