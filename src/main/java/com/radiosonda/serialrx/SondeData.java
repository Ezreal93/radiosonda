package com.radiosonda.serialrx;

import java.time.LocalTime;

public class SondeData {

    public double pressure;
    public double temperature;
    public double humidity;
    public boolean isValid;
    public LocalTime timestamp;


    public SondeData setPressure(double pressure) {
        this.pressure = pressure;
        return this;
    }

    public SondeData setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public SondeData setHumidity(double humidity) {
        this.humidity = humidity;
        return this;
    }

    public SondeData setValid(boolean valid) {
        isValid = valid;
        return this;
    }

    @Override
    public String toString() {
        return "SondeData{" +
                "pressure=" + pressure +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", isValid=" + isValid +
                '}';
    }

    public SondeData(double pressure, double temperature, double humidity) {
        this.isValid = true;
        this.pressure = pressure;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public SondeData() {
        this.isValid = false;
    }
}
