# NigerianCovid19API
A RESTful API to nigerian covid19 statistics

This is a RESTful API to get latest Nigerian covid19 statistics. 
This work leverage on sink-opuba's  covid-19-nigeria-api project at
https://github.com/sink-opuba/covid-19-api to get the statistics 
from NCDC website and provide restful endpoints to get some
specific info.

Note: to change the source of the covid19 data, edit the covidAppConfig.txt 
file at the root of the project, but bear it in mind that you may have to
handle some things in the json format returned by your source is totally
different from the default one.