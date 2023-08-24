package com.food.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class StockActionJob implements Job {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Stock Action Job execute: " + df.format(new Date()));
    }
}
