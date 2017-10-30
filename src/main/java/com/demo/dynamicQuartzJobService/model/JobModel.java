package com.demo.dynamicQuartzJobService.model;

public class JobModel {
    
    private String name;
    private String cronExpression;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCronExpression() {
        return cronExpression;
    }
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    

}
