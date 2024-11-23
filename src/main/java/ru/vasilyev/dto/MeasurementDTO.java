package ru.vasilyev.dto;

import java.io.Serializable;

public class MeasurementDTO implements Serializable {
    private double value;

    private boolean raining;

    private SensorDTO sensor;

    public MeasurementDTO() {

    }

    public MeasurementDTO(double value, boolean isRaining, SensorDTO sensor) {
        this.value = value;
        this.raining = isRaining;
        this.sensor = sensor;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
