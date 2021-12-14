# NigerianCovid19API
A RESTful API to nigerian covid19 statistics

This is a RESTful API to get latest Nigerian covid19 statistics. 
This work leverage on [sink-opuba's covid-19-nigeria-api project](https://github.com/sink-opuba/covid-19-nigeria-api) at
 get the statistics from NCDC website and provide restful endpoints to get some
specific information.

This project is hosted on heroku at https://nigerian-covid-19-api.herokuapp.com. feel free to call the api

## Customization
Note: to change the source of the covid19 data, change the value for sourceApiUri
in application.properties file, but bear it in mind that you may have to
handle some things if the json format returned by your source is totally
different from the default one.

## API
https://nigerian-covid-19-api.herokuapp.com/summary - to get summary of national covid19 statistics

https://nigerian-covid-19-api.herokuapp.com/details - to get data for all states 
