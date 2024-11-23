package ru.vasilyev.dto;

import java.io.Serializable;

public class SensorDTO implements Serializable {
    private String name;

    public SensorDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorDTO() {

    }
}

