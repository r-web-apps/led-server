package me.rubeen.arduino.led.server;

import me.rubeen.arduino.led.server.entities.JSONDisplayObject;
import me.rubeen.arduino.led.server.entities.RGBStripe;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class ServiceRGB {
    private Map<Integer, RGBStripe> rgbStripes = new HashMap<>();
    private Map<Integer, JSONDisplayObject> jsonDisplayObjectMap = new HashMap<>();

    public ServiceRGB() throws UnknownHostException {
        addToList(new RGBStripe(InetAddress.getByName("192.168.178.93"), 0, "https://www.derrywood.com/wp-content/uploads/2017/03/RGB-LED-Strip.jpg", "LED-Leiste (Spiegel)", false));
        //addToList(new RGBStripe(InetAddress.getByName("192.168.178.99"), 1, "https://i.pinimg.com/originals/fa/b4/a0/fab4a045779b34c51d7a03cae6aee610.jpg", "LED-Leiste (Bett)", false));
        //addToList(new RGBStripe(InetAddress.getByName("192.168.178.182"), 2, "https://www.countrystyle.se/7135-home_default/light-chain-bulb-battery.jpg", "Lichterkette (Kommode)", true));
    }

    public Stream<RGBStripe> getRgbStripes() {
        return rgbStripes.values().parallelStream();
    }

    public Stream<JSONDisplayObject> getJsonDisplayObjects() {
        return jsonDisplayObjectMap.values().parallelStream();
    }

    public RGBStripe getStripe(int i) {
        return rgbStripes.get(i);
    }

    public void addToList(RGBStripe rgbStripe) {
        rgbStripes.put(rgbStripe.getId(), rgbStripe);
        jsonDisplayObjectMap.put(rgbStripe.getId(), new JSONDisplayObject(rgbStripe.getId(), rgbStripe.getImageSrc(), rgbStripe.getName(), rgbStripe.getAutoEnabled()));
    }
}
