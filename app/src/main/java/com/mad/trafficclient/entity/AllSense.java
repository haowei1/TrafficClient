package com.mad.trafficclient.entity;

import com.google.gson.annotations.SerializedName;

public class AllSense {

    /**
     * pm2.5 : 293
     * co2 : 1912
     * LightIntensity : 2328
     * humidity : 25
     * temperature : 39
     * Status : 2
     */

    @SerializedName("pm2.5")
    private int pm;
    private int co2;
    private int LightIntensity;
    private int humidity;
    private int temperature;
    private int Status;

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getLightIntensity() {
        return LightIntensity;
    }

    public void setLightIntensity(int LightIntensity) {
        this.LightIntensity = LightIntensity;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    @Override
    public String toString() {
        return "AllSense{" +
                "pm=" + pm +
                ", co2=" + co2 +
                ", LightIntensity=" + LightIntensity +
                ", humidity=" + humidity +
                ", temperature=" + temperature +
                ", Status=" + Status +
                '}';
    }
}
