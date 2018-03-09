package com.yrgyz;

import org.quartz.*;

import java.io.File;

/**
 * Created by ZBaimanov on 01.03.2018.
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class LogMonitorJob implements Job {

    private static final String MINUTES_COUNTER = "minutesCounter";
    private static final String FILE_PATH = "C:/Users/user/dev/server.log";

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ReadFileLast rf = new ReadFileLast();
        File file = new File(FILE_PATH);
        if (rf.hasLogFileError(file, 10)) {
            JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
            int counter = map.getInt(MINUTES_COUNTER);
            map.put(MINUTES_COUNTER, ++counter);
            if (counter > 10) {
                System.out.println("ACHTUNG");
                map.put(MINUTES_COUNTER, 0);
            }
        }
    }
}