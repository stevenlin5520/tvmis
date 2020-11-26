package com.steven.television.controller;

import com.steven.television.services.BreakRuleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author steven
 * @desc
 * @date 2020/11/23 11:06
 */
@Component
public class TimerController {

    @Resource
    private BreakRuleService breakRuleService;

    /**
     * <p>定时器审核供应商违规信息</p>
     * <p>
     *     违规超过count1次数，插入一条告警记录（type=2，status=1）；
     *     违规超过count2次数，加入一条黑名单记录（type=3，status=1），删除告警记录(status=-1)，将供应商审核状态置为失信，删除违规记录
     * </p>
     */
    @Scheduled(cron = "0 0 * * * ? ")
    public void autoTelevisionAddBreak(){
        System.out.println("定时器执行告警、黑名单审核");
        breakRuleService.autoSupplierWarn();
        breakRuleService.autoSupplierBacklist();
    }

}
