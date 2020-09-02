package devadeboye.Nigeria.Covid19RestAPI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StateFigure {
    private int id;
    private String state;
    private int confirmed;
    private int onAdmission;
    private int discharged;
    private int death;

    public StateFigure(){
    }

    public StateFigure(int id, String state, int confirmed, int onAdmission, int discharged, int death ) {
        this.id = id;
        this.state = state;
        this.confirmed = confirmed;
        this.onAdmission = onAdmission;
        this.discharged = discharged;
        this.death = death;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getOnAdmission() {
        return onAdmission;
    }

    public void setOnAdmission(int onAdmission) {
        this.onAdmission = onAdmission;
    }

    public int getDischarged() {
        return discharged;
    }

    public void setDischarged(int discharged) {
        this.discharged = discharged;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }
}
