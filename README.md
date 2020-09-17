# NigerianCovid19API
A RESTful API to nigerian covid19 statistics

This is a RESTful API to get latest Nigerian covid19 statistics. 
This work leverage on [sink-opuba's covid-19-nigeria-api project](https://github.com/sink-opuba/covid-19-api) at
 get the statistics from NCDC website and provide restful endpoints to get some
specific info.

## Customization
Note: to change the source of the covid19 data, edit the covidAppConfig.txt 
file at the root of the project, but bear it in mind that you may have to
handle some things in the json format returned by your source is totally
different from the default one.

## API
domain/summary - to get summary of national covid19 statistics

domain/details - to get data for all states

domain/state/statename - to get data for specific state
e.g http://localhost:8080/state/Lagos. (Note: statename must start with uppercase) 