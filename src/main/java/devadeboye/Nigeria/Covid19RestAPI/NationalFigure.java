package devadeboye.Nigeria.Covid19RestAPI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NationalFigure {
    private int id;
    private int tested;
    private int confirmed;
    private int active;
    private int discharged;
    private int death;

    public NationalFigure(){
    }

    public NationalFigure(int id, int tested, int confirmed, int active, int discharged, int death) {
        this.id = id;
        this.tested = tested;
        this.confirmed = confirmed;
        this.active = active;
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

    public int getTested() {
        return tested;
    }

    public void setTested(int tested) {
        this.tested = tested;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
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
