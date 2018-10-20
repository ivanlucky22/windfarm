# Wind Farm
An application that lets customer control daily Capacity factor and daily amount of produced electricity.

# Swagger
Check out API documentation using following link http://localhost:8080/swagger-ui.html#/
You can easily test API with swagger by clicking "Try it out" button on the above swagger page.

# Implementation
The task was to create REST API that will grep (and modify) data from the database aggregated per day.
The most efficient way to aggregate the data - use native SQL grouping. So for all of the services a grouped data by day within single wind farm were enough.
Service for calculating capacity factor requires to have grouped data by day and divide this data onto maximum possible capacity per day.
Service for calculating actually produced energy needs only to have grouped data by day. 
`From the task it was not absolutely clear if produced energy calculating also has to be breaked down per days or no,
 so to satisfy both needs and as efforts were extremely small -  2 endpoint were provided: /produced and /producedSum`


No specific Error Handling was provided because the default one is good enough for the test task
{
  "timestamp": "2018-10-20T16:07:24.561+0000",
  "status": 400,
  "error": "Bad Request",
  "message": "Required Long parameter 'winFarmId' is not present",
  "path": "/api/producedSum"
}