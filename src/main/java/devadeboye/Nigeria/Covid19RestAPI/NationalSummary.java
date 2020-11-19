/*package devadeboye.Nigeria.Covid19RestAPI;

import org.json.simple.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NationalSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer totalSampleTested;
    private Integer totalConfirmedCases;
    private Integer totalActiveCases;
    private Integer discharged;
    private Integer death;

    public void setTotalSampleTested(Integer totalSampleTested) {
        this.totalSampleTested = totalSampleTested;
    }

    public void setTotalConfirmedCases(Integer totalConfirmedCases) {
        this.totalConfirmedCases = totalConfirmedCases;
    }

    public void setTotalActiveCases(Integer totalActiveCases) {
        this.totalActiveCases = totalActiveCases;
    }

    public void setDischarged (Integer discharged) {
        this.discharged = discharged;
    }

    public void setDeath (Integer death) {
        this.death = death;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getNationalSummary() {
        JSONObject result = new JSONObject();
        result.put("totalSampleTested", totalSampleTested);
        result.put("totalConfirmedCases", totalConfirmedCases);
        result.put("totalActiveCases", totalActiveCases);
        result.put("discharged", discharged);
        result.put("death", death);
        return result;
    }
}
*/