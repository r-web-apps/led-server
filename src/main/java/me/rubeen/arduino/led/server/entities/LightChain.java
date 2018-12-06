package me.rubeen.arduino.led.server.entities;

import java.net.Inet4Address;
import java.net.InetAddress;

public class LightChain implements IShiningObject {

    private final InetAddress address;
    private final int id;
    private String imageSrc;
    private String name;
    private Boolean autoEnabled;

    public LightChain(Inet4Address address, int id, String imageSrc, String name, Boolean autoEnabled) {
        this.address = address;
        this.id = id;
        this.imageSrc = imageSrc;
        this.name = name;
        this.autoEnabled = autoEnabled;
    }

    @Override
    public void turn(LightState lightState) {

    }

    @Override
    public InetAddress getAddress() {
        return address;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getImageSrc() {
        return imageSrc;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Boolean getAutoEnabled() {
        return autoEnabled;
    }

    @Override
    public void setAutoEnabled(boolean autoEnabled) {
        this.autoEnabled = autoEnabled;
    }
}
