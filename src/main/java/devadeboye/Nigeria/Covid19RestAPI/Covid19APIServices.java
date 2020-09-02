package devadeboye.Nigeria.Covid19RestAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Covid19APIServices {

    @Autowired
    private NationalFigureRepository nationalRepo;
    @Autowired
    private StateFigureRepository stateRepo;
    @Autowired
    private GetExternalData getExternalData;

    public JSONObject getSummary() {
        return getExternalData.getNationalData();
    }

    public JSONObject getFullDetails() {
        return getExternalData.getStateData();
    }

    public JSONObject getStateData(String state) {
        // state arg is state name
        JSONObject covidData = getExternalData.getStateData();
        return (JSONObject) covidData.get(state);
    }
}
