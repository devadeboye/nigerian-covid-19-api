package devadeboye.Nigeria.Covid19RestAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;

@Service
public class GetExternalData {
    private JSONObject nationalData;
    private JSONObject stateData = new JSONObject();
    private JSONObject rawCovidData;

    public GetExternalData() {
    }

    public JSONObject getNationalData() {
        setNationalDataVariable();
        return nationalData;
    }

    private void setNationalDataVariable() {
        if (rawCovidData == null) { setRawCovidData(); }
        JSONObject rawCovidDataCopy = (JSONObject) this.rawCovidData.clone();
        rawCovidDataCopy.remove("states");
        this.nationalData = rawCovidDataCopy;
    }

    private void setRawCovidData() {
        JSONObject parsedSourceDataObj = (JSONObject) parseSourceData();
        parsedSourceDataObj = (JSONObject) parsedSourceDataObj.get("data");
        this.rawCovidData = parsedSourceDataObj;
    }

    private Object parseSourceData() {
        JSONParser parser = new JSONParser();
        Object parsedSourceData = null;
        String sourceData = getDataFromSource();
        try {
            parsedSourceData = parser.parse(sourceData);
        } catch (ParseException e) {
            System.out.println("unable to parse the json data!");
        }
        return parsedSourceData;
    }

    private String getDataFromSource() {
        final String uri = customFileReader("CovidAppConfig.txt");
        RestTemplate restTemplate = new RestTemplate();
        String dataString = restTemplate.getForObject(uri, String.class);
        return dataString;
    }

    public JSONObject getStateData() {
        setStateDataVariable();
        return stateData;
    }

    private void setStateDataVariable() {
        if (rawCovidData == null) {setRawCovidData(); }
        JSONObject rawCovidDataCopy = (JSONObject) this.rawCovidData.clone();
        rawCovidDataCopy = removeNationSummaryFromData(rawCovidDataCopy);
        JSONArray allStateCovidData = (JSONArray) rawCovidDataCopy.get("states");
        JSONObject filteredStateData = filterStateData(allStateCovidData);
        this.stateData =filteredStateData;
    }

    private JSONObject removeNationSummaryFromData(JSONObject data) {
        String elementsToBeRemoved[] = {
                "discharged", "death", "totalActiveCases",
                "totalConfirmedCases", "totalSamplesTested"
        };
        data = removeJsonObjectElements(data, elementsToBeRemoved);
        return data;
    }

    private JSONObject removeJsonObjectElements(JSONObject jsonObject, String args[]) {
        for (String jsonKey : args) {
            jsonObject.remove(jsonKey);
        }
        return jsonObject;
    }

    private JSONObject filterStateData(JSONArray allStateCovidData) {
        JSONObject filteredStateData = new JSONObject();
        for(Object eachStateData : allStateCovidData) {
            JSONObject eachStateDataJSONObject = (JSONObject) eachStateData;
            eachStateDataJSONObject.remove("_id");
            String stateKey = (String) eachStateDataJSONObject.get("state");
            putItemIntoJSONObject(filteredStateData, stateKey, eachStateDataJSONObject);
        }
        return filteredStateData;
    }

    private void putItemIntoJSONObject(JSONObject objectName, String key, JSONObject value) {
        try{
            objectName.put(key, value);
        }catch (NullPointerException e) {
            System.out.println("There is problem in adding data to stateData Object");
        }
    }

    private String customFileReader(String location){
        String fileContent = null;
        try {
            File file = new File(location);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            fileContent = reader.readLine();
        }catch (IOException e) {
            System.out.println("file " + location + " is not found!");
        }
        return fileContent;
    }
}
