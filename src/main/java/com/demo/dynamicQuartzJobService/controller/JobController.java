package com.demo.dynamicQuartzJobService.controller;

import java.util.List;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.dynamicQuartzJobService.enums.JobGroup;
import com.demo.dynamicQuartzJobService.job.DailyJob;
import com.demo.dynamicQuartzJobService.job.OneTimeJob;
import com.demo.dynamicQuartzJobService.job.WeeklyJob;
import com.demo.dynamicQuartzJobService.model.JobModel;

@Controller
public class JobController {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ApplicationContext context;;

    @ResponseBody
    @RequestMapping(value = "/job/create/daily", method = RequestMethod.POST)
    public ResponseEntity<JobModel> dailyJob(@RequestBody JobModel jobModel) throws SchedulerException {
        JobDetail jobDetail = context.getBean(JobDetail.class, jobModel.getName(), JobGroup.DAILY_GROUP.name(),
                DailyJob.class);
        Trigger cronTrigger = context.getBean(Trigger.class, jobModel.getCronExpression(), JobGroup.DAILY_GROUP.name());

        scheduler.scheduleJob(jobDetail, cronTrigger);

        return new ResponseEntity<JobModel>(jobModel, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/job/create/weekly", method = RequestMethod.POST)
    public ResponseEntity<JobModel> weeklyJob(@RequestBody JobModel jobModel) throws SchedulerException {
        JobDetail jobDetail = context.getBean(JobDetail.class, jobModel.getName(), JobGroup.WEEKLY_GROUP.name(),
                WeeklyJob.class);
        Trigger cronTrigger = context.getBean(Trigger.class, jobModel.getCronExpression(),
                JobGroup.WEEKLY_GROUP.name());

        scheduler.scheduleJob(jobDetail, cronTrigger);

        return new ResponseEntity<JobModel>(jobModel, HttpStatus.CREATED);

    }

    @ResponseBody
    @RequestMapping(value = "/job/create/oneTime", method = RequestMethod.POST)
    public ResponseEntity<JobModel> oneTimeJob(@RequestBody JobModel jobModel) throws SchedulerException {
        JobDetail jobDetail = context.getBean(JobDetail.class, jobModel.getName(), JobGroup.ONE_TIME_GROUP.name(),
                OneTimeJob.class);
        Trigger cronTrigger = context.getBean(Trigger.class, jobModel.getCronExpression(),
                JobGroup.ONE_TIME_GROUP.name());

        scheduler.scheduleJob(jobDetail, cronTrigger);

        return new ResponseEntity<JobModel>(jobModel, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping("job/list")
    public List<String> jobList() throws SchedulerException {
        return scheduler.getJobGroupNames();
    }

    @ResponseBody
    @RequestMapping(value = "job/delete/daily", method = RequestMethod.POST)
    public ResponseEntity<Boolean> deleteJob(@RequestBody JobModel jobModel) throws SchedulerException {
        JobKey jobKey = new JobKey(jobModel.getName(), JobGroup.DAILY_GROUP.name());
        return new ResponseEntity<Boolean>(scheduler.deleteJob(jobKey), HttpStatus.OK);
    }

}
