package com.yrgyz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by ZBaimanov on 01.03.2018.
 */
public class SimpleTrigger {

    private static final String MINUTES_COUNTER = "minutesCounter";

    public static void main(String[] args) throws SchedulerException {

        JobDetail job = JobBuilder.newJob(LogMonitorJob.class)
                .withIdentity("logMonitorJob", "group1").build();

        job.getJobDataMap().put(MINUTES_COUNTER, 10);

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("logMonitorTrigger", "group1")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(1).repeatForever()
                ).build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
