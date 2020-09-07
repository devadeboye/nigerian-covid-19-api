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

    private void parseJson() {
        final String uri = CustomFileReader("CovidAppConfig.txt");
        RestTemplate restTemplate = new RestTemplate();
        String dataString = restTemplate.getForObject(uri, String.class);
        JSONParser parser = new JSONParser();
        Object dataStringObject = null;

        try {
            // read json data
            dataStringObject = parser.parse(dataString);
        } catch (ParseException e) {
            System.out.println("unable to parse the json data!");
        }

        // typecasting dataStringObject to JSONObject
        JSONObject fileContent = (JSONObject) dataStringObject;
        fileContent = (JSONObject) fileContent.get("data");
        this.rawCovidData = fileContent;
    }

    private void populateVariables() {
        parseJson();
        JSONObject covidData1 = (JSONObject) this.rawCovidData.clone();
        JSONObject covidData2 = (JSONObject) this.rawCovidData.clone();

        // prepare national data summary
        covidData1.remove("states");
        this.nationalData = covidData1;

        // prepare state data by removing unwanted data
        covidData2.remove("discharged");
        covidData2.remove("death");
        covidData2.remove("totalActiveCases");
        covidData2.remove("totalConfirmedCases");
        covidData2.remove("totalSamplesTested");
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
                System.out.println("There is problem in adding data to stateData Object");
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
