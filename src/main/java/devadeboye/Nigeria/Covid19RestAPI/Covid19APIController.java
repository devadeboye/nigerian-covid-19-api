package devadeboye.Nigeria.Covid19RestAPI;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class Covid19APIController {

    @Autowired
    private Covid19APIServices services;

    @GetMapping("/")
    public JSONObject home() {
        // TODO: serve index.html file from this later
        return services.getNationalDataSummary();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/summary")
    public JSONObject summary() {
        return services.getNationalDataSummary();
    }

    @GetMapping("/details")
    public JSONObject details() {
        return services.getNationalDataBreakdown();
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<JSONObject> get(@PathVariable String state) {
        try {
            JSONObject stateData = services.getSpecificStateData(state);
            return new ResponseEntity<JSONObject>(stateData, HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<JSONObject>(HttpStatus.NOT_FOUND);
        }
    }
}
