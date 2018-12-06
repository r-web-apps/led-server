package me.rubeen.arduino.led.server.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

public class RGBStripe implements IShiningObject {
    private final InetAddress address;
    private Logger LOG = LoggerFactory.getLogger(this.getClass());
    private int id;
    private String imageSrc;
    private String name;
    private Boolean autoEnabled;

    public RGBStripe(InetAddress address, int id, String imageSrc, String name, Boolean autoEnabled) {
        this.address = address;
        this.id = id;
        this.imageSrc = imageSrc;
        this.name = name;
        this.autoEnabled = autoEnabled;
    }

    @Override
    @Async
    public void turn(LightState lightState) {
        try {
            sendRequest(new URL(MessageFormat.format("http://{0}{1}", address.getHostAddress(), lightState.equals(LightState.ON) ? "/all" : "/off")));
        } catch (MalformedURLException e) {
            LOG.error("URL is maleformed: {}", address.getHostAddress(), e);
        } catch (IOException e) {
            LOG.error("Error while opening connection for {}", address.getHostAddress(), e);
        }
    }

    @Async
    public void set(final LightColor lightColor, final Integer brightness) {
        try {
            sendRequest(new URL(MessageFormat.format("http://{0}/{1}/{2}", address.getHostAddress(), getStringFromLightColor(lightColor), brightness)));
        } catch (MalformedURLException e) {
            LOG.error("URL is malformed: {}", address.getHostAddress(), e);
        } catch (IOException e) {
            LOG.error("Error while opening connection for {}", address.getHostAddress(), e);
        }
    }

    @Async
    public void set(final LightColor lightColor) {
        try {
            sendRequest(new URL(MessageFormat.format("http://{0}/{1}", address.getHostAddress(), getStringFromLightColor(lightColor))));
        } catch (MalformedURLException e) {
            LOG.error("URL is malformed: {}", address.getHostAddress(), e);
        } catch (IOException e) {
            LOG.error("Error while opening connection for {}", address.getHostAddress(), e);
        }
    }

    private String getStringFromLightColor(LightColor lightColor) {
        return lightColor.equals(LightColor.WHITE) ? "all"
                : lightColor.equals(LightColor.RED) ? "r"
                : lightColor.equals(LightColor.GREEN) ? "g"
                : lightColor.equals(LightColor.BLUE) ? "b" : null;
    }

    private void sendRequest(URL url) throws IOException {
        LOG.info("Accessing: " + url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(1000);
        connection.setConnectTimeout(1000);
        connection.connect();
        int status = connection.getResponseCode();
        connection.disconnect();
        LOG.info("Successfully connected to {} -- STATUS: {}", address.getHostAddress(), status);
    }

    @Override
    public InetAddress getAddress() {
        return address;
    }

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

    public enum LightColor {
        RED, GREEN, BLUE, WHITE
    }
}
