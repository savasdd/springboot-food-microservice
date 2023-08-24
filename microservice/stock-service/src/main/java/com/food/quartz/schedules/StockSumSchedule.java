package com.food.quartz.schedules;

import com.food.quartz.job.StockSumJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "stock.sum.schedule", havingValue = "true")
public class StockSumSchedule {
    final ApplicationContext applicationContext;

    public StockSumSchedule(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Bean(name = "jobTwo")
    public JobDetailFactoryBean jobDetailTwo() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(StockSumJob.class);
        jobDetailFactory.setName("Qrtz_Job_Two_Detail");
        jobDetailFactory.setDescription("Invoke Job service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public SimpleTriggerFactoryBean triggerTwo(@Qualifier("jobTwo") JobDetail job) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);
        int frequencyInSec = 5;
        log.info("Configuring trigger to fire every {} seconds", frequencyInSec);
        trigger.setRepeatInterval(frequencyInSec * 1000);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setName("Qrtz_Trigger_Job_Two");
        return trigger;
    }
}
