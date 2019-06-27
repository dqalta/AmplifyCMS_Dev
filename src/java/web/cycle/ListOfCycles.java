/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.cycle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class ListOfCycles implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        JobDetail SendMails = JobBuilder.newJob(SendMails.class)
                .withIdentity("SendMails", "grupo1").build();

        try {

            Trigger ScheduleSendMails = TriggerBuilder
                    .newTrigger()
                    .withIdentity("ScheduleSendMails", "group1")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *"))
                    .build();

            Scheduler ciclo = new StdSchedulerFactory().getScheduler();
            ciclo.start();
            ciclo.scheduleJob(SendMails, ScheduleSendMails);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
