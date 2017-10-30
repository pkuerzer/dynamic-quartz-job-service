"# dynamic-quartz-job-service" 

To schedule a job just send a post request to one of the endpoints.

For example:
```
http://localhost:8080/job/create/daily
RequestBody: {"name":"Job 2", "cronExpression":"10 * * * * ?"}
```
