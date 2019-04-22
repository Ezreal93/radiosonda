package com.radiosonda.serialrx;

/**
 *
 * @author manuel
 */
public class SondeData {

    double pressure;
    double temperature;
    double humidity;
    boolean isValid;

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
