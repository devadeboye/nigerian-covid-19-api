package devadeboye.Nigeria.Covid19RestAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class GetExternalData {
    private JSONObject nationalData;
    private JSONObject stateData = new JSONObject();
    private JSONObject rawCovidData;

    public GetExternalData() {
    }

    private void getDataFromSource() {
        final String uri = "https://covidnigeria.herokuapp.com/api";
        RestTemplate restTemplate = new RestTemplate();
        String dataString = restTemplate.getForObject(uri, String.class);
        JSONParser parser = new JSONParser();
        JSONObject obj;

        try {
            obj = (JSONObject)parser.parse(dataString);
            System.out.println(obj);
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void parseJson() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("foo.json")) {
            // read json data from file
            Object obj = parser.parse(reader);

            // typecasting obj to JSONObject
            JSONObject fileContent = (JSONObject) obj;
            fileContent = (JSONObject) fileContent.get("data");
            this.rawCovidData = fileContent;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateVariables() {
        parseJson();
        JSONObject covidData1 = (JSONObject) this.rawCovidData.clone();
        JSONObject covidData2 = (JSONObject) this.rawCovidData.clone();

        // prepare national data summary
        covidData1.remove("states");
        this.nationalData = covidData1;

        // prepare state data
        covidData2.remove("discharged");
        covidData2.remove("death");
        covidData2.remove("totalActiveCases");
        covidData2.remove("totalConfirmedCases");
        covidData2.remove("totalSamplesTested");
        // this.stateData = (JSONArray) covidData2.get("states");
        JSONArray temp = (JSONArray) covidData2.get("states");
        for(Object item : temp) {
            JSONObject jsonItem = (JSONObject) item;
            jsonItem.remove("_id");
            // cast state name to string
            String stateKey = (String) jsonItem.get("state");
            try{
                // add key and value to stateData JsonObject
                this.stateData.put(stateKey, jsonItem);
            }catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject getNationalData() {
        populateVariables();
        return nationalData;
    }

    public JSONObject getStateData() {
        populateVariables();
        return stateData;
    }
}
