package me.rubeen.arduino.led.server.entities;

import java.net.Inet4Address;
import java.net.InetAddress;

public interface IShiningObject {
    void turn(LightState lightState);

    InetAddress getAddress();

    int getId();

    String getImageSrc();

    String getName();

    Boolean getAutoEnabled();

    void setAutoEnabled(boolean autoEnabled);

    enum LightState {
        OFF, ON;
    }
}
