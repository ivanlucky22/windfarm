# Wind Farm
An application that lets customer control daily Capacity factor and daily amount of produced electricity.

# Swagger
Check out API documentation using following link http://localhost:8080/swagger-ui.html#/
You can easily test API with swagger by clicking "Try it out" button on the above swagger page.

# Data model
Wind Farm table eft with no changes.
In Hourly Production Data table the timestamp column was divided onto 2 columns: dateId and timeId to provide flexibility in grouping by day. Date id is the date in format "yyyyMMdd".
 Time id is just amount of seconds passed from midnight to the certain time if it were 01.01.1970.

# Implementation
The task was to create REST API that will grep (and modify) data from the database aggregated per day.
The most efficient way to aggregate the data - use native SQL grouping. So for all of the services a grouped data by day within single wind farm were enough.
 To return data from grouped query a new VO com.pexapark.windfarm.vo.ElectricityProductionAggregatedPerFarmAndDateVO was introduced.
Service for calculating capacity factor requires to have grouped data by day and divide this data onto maximum possible capacity per day.
Service for calculating actually produced energy needs only to have grouped data by day. 
>From the task it was not absolutely clear if produced energy calculating also has to be breaked down per days or no,
 so to satisfy both needs and as efforts were extremely small -  2 endpoint were provided: /produced and /producedSum

# DST
Handling DST is required only for cases with calculating capacity factor.
 To find capacity factor for day it is needed to sum up all produced energy for the day and divide onto maximum capacity per day.
  For regular day maximum capacity is 24(hours) multiplied onto max capacity per hour (10MW), but for days on DST edges amount of hours might be 23 or 25.

# Error Handling
No specific Error Handling was provided because the default one is good enough for the test task. Here is an example:
```
{
  "timestamp": "2018-10-20T16:07:24.561+0000",
  "status": 400,
  "error": "Bad Request",
  "message": "Required Long parameter 'winFarmId' is not present",
  "path": "/api/producedSum"
}
```

# Initial data
For quick testing some initial data were provided with JPA in the following method: com.pexapark.windfarm.WindFarmApplication.run.
(I've decided not to provide SQL scripts to run on startup because of lack of time.)

# DB
Connection parameters by default are following:
URL: jdbc:postgresql://localhost:5432/windFarmDB
Username=postgres
Password=postgres
