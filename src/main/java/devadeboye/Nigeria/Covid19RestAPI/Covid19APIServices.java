package devadeboye.Nigeria.Covid19RestAPI;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Covid19APIServices {

    @Autowired
    private GetExternalData getExternalData;
    //@Autowired
    //private NationalSummaryRepository nationalSummaryRepository;

    public JSONObject getNationalDataSummary() {
        return getExternalData.getNationalData();
    }

    public JSONObject getNationalDataBreakdown() {
        return getExternalData.getStateData();
    }

    public JSONObject getSpecificStateData(String state) {
        // state arg is state name
        JSONObject covidData = getExternalData.getStateData();
        return (JSONObject) covidData.get(state);
    }
}
