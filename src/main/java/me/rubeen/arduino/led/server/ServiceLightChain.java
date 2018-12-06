package me.rubeen.arduino.led.server;

import me.rubeen.arduino.led.server.entities.LightChain;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ServiceLightChain {
    private List<LightChain> lightChains = new LinkedList<>();

    public Stream<LightChain> getLightChains() {
        return lightChains.parallelStream();
    }

    public void addToList(LightChain lightChain) {
        lightChains.add(lightChain);
    }

    public LightChain getChain(int id) {
        return null;
    }
}
