package com.radiosonda.serialrx;

import java.util.Random;

public class SondeDataGenerator {
    public float temperature = 20;
    public float pressure = 950;
    public float humidity = 35;

    public float humidityVariance = 0.5f;
    public float pressureVariance = 20;
    public float temperatureVariance = 1;

    public Random random;

    public SondeDataGenerator() {
        this.random = new Random();
    }

    public SondeData generate() {
        SondeData sondeData = new SondeData()
                .setValid(true)
                .setHumidity(generateRandomNumber(humidity, humidityVariance))
                .setPressure(generateRandomNumber(pressure, pressureVariance))
                .setTemperature(generateRandomNumber(temperature, temperatureVariance));
        return sondeData;
    }

    float generateRandomNumber(float mean, float variance) {
        return ((random.nextFloat() - 0.5f) * variance) + mean;
    }

}
