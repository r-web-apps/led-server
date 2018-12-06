package me.rubeen.arduino.led.server.entities;

import com.fasterxml.jackson.annotation.JsonGetter;

public class JSONDisplayObject {
    private int id;
    private String imageSrc;
    private String name;
    private boolean autoEnabled;

    public JSONDisplayObject(int id, String imageSrc, String name, boolean autoEnabled) {
        this.id = id;
        this.imageSrc = imageSrc;
        this.name = name;
        this.autoEnabled = autoEnabled;
    }

    @JsonGetter
    public int getId() {
        return id;
    }

    @JsonGetter
    public String getImageSrc() {
        return imageSrc;
    }

    @JsonGetter
    public String getName() {
        return name;
    }

    @JsonGetter
    public boolean isAutoEnabled() {
        return autoEnabled;
    }
}
