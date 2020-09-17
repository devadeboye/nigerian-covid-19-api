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

    private String CustomFileReader(String location){
        File file;
        BufferedReader reader = null;
        String fileContent = null;
        try {
            file = new File(location);
            reader = new BufferedReader(new FileReader(file));
            fileContent = reader.readLine();
        }catch (IOException e) {
            System.out.println("file " + location + " is not found!");
        }
        return fileContent;
    }

    private String getDataFromSource() {
        final String uri = CustomFileReader("CovidAppConfig.txt");
        RestTemplate restTemplate = new RestTemplate();
        String dataString = restTemplate.getForObject(uri, String.class);
        return dataString;
    }

    private void parseSourceData() {
        JSONParser parser = new JSONParser();
        Object parsedSourceData = null;
        String sourceData = getDataFromSource();
        try {
            parsedSourceData = parser.parse(sourceData);
        } catch (ParseException e) {
            System.out.println("unable to parse the json data!");
        }
        JSONObject parsedSourceDataObj = (JSONObject) parsedSourceData;
        parsedSourceDataObj = (JSONObject) parsedSourceDataObj.get("data");
        this.rawCovidData = parsedSourceDataObj;
    }

    private void setNationalDataVariable() {
        if (rawCovidData == null) { parseSourceData(); }
        JSONObject rawCovidDataCopy = (JSONObject) this.rawCovidData.clone();
        rawCovidDataCopy.remove("states");
        this.nationalData = rawCovidDataCopy;
    }

    private void removeJsonObjectElements(JSONObject jsonObject, String args[]) {
        for (String jsonKey : args) {
            jsonObject.remove(jsonKey);
        }
    }

    private void setStateDataVariable() {
        if (rawCovidData == null) { parseSourceData();}
        JSONObject rawCovidDataCopy = (JSONObject) this.rawCovidData.clone();
        String elementsToBeRemoved[] = {
                "discharged", "death", "totalActiveCases",
                "totalConfirmedCases", "totalSamplesTested"
        };
        removeJsonObjectElements(rawCovidDataCopy, elementsToBeRemoved);
        JSONArray allStateCovidData = (JSONArray) rawCovidDataCopy.get("states");

        for(Object eachStateData : allStateCovidData) {
            JSONObject eachStateDataJSONObject = (JSONObject) eachStateData;
            // remove unwanted element
            eachStateDataJSONObject.remove("_id");
            // get state name to be use as key and cast to string
            String stateKey = (String) eachStateDataJSONObject.get("state");
            try{
                // add key and value to stateData JsonObject
                this.stateData.put(stateKey, eachStateDataJSONObject);
            }catch (NullPointerException e) {
                System.out.println("There is problem in adding data to stateData Object");
            }
        }
    }

    public JSONObject getNationalData() {
        setNationalDataVariable();
        return nationalData;
    }

    public JSONObject getStateData() {
        setStateDataVariable();
        return stateData;
    }
}
