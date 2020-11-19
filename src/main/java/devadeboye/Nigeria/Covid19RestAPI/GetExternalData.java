package devadeboye.Nigeria.Covid19RestAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GetExternalData {
    private JSONObject nationalData;
    private JSONObject stateData = new JSONObject();
    private JSONObject rawCovidData;
    @Value("${sourceApiUri}")
    private String sourceApiUri;

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
        JSONObject parsedSourceDataObj = parseSourceData();
        parsedSourceDataObj = (JSONObject) parsedSourceDataObj.get("data");
        this.rawCovidData = parsedSourceDataObj;
    }

    private JSONObject parseSourceData() {
        JSONParser parser = new JSONParser();
        JSONObject parsedSourceData = new JSONObject();
        String sourceData = getDataFromSource();
        try {
            parsedSourceData = (JSONObject) parser.parse(sourceData);
        } catch (ParseException e) {
            System.out.println("unable to parse the json data!");
        }
        return parsedSourceData;
    }

    private String getDataFromSource() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(sourceApiUri, String.class);
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
        this.stateData= filterStateData(allStateCovidData);
    }

    private JSONObject removeNationSummaryFromData(JSONObject data) {
        String[] elementsToBeRemoved = {
                "discharged", "death", "totalActiveCases",
                "totalConfirmedCases", "totalSamplesTested"
        };
        return removeJsonObjectElements(data, elementsToBeRemoved);
    }

    private JSONObject removeJsonObjectElements(JSONObject jsonObject, String[] args) {
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

    @SuppressWarnings("unchecked")
    private void putItemIntoJSONObject(JSONObject objectName, String key, JSONObject value) {
        try{
            objectName.put(key, value);
        }catch (NullPointerException e) {
            System.out.println("There is problem in adding data to stateData Object");
        }
    }
}
