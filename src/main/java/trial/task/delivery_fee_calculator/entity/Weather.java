package trial.task.delivery_fee_calculator.entity;

import jakarta.persistence.*;


@Entity
@Table

public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private long timestamp;
    @Column
    private String name;
    @Column
    private int wmocode;
    @Column
    private float airtemperature;
    @Column
    private float windspeed;
    @Column
    private String phenomenon;

    public Weather(long timestamp) {
        this.timestamp = timestamp;
    }

    public Weather() {

    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWmocode() {
        return wmocode;
    }

    public void setWmocode(int wmocode) {
        this.wmocode = wmocode;
    }

    public float getAirtemperature() {
        return airtemperature;
    }

    public void setAirtemperature(float airtemperature) {
        this.airtemperature = airtemperature;
    }

    public float getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(float windspeed) {
        this.windspeed = windspeed;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }
}
