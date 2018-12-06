package me.rubeen.arduino.led.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.rubeen.arduino.led.server.entities.JSONDisplayObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(path = "/get")
@Component
public class GetController {
    private ServiceRGB rgbService;

    public GetController(ServiceRGB rgbService) {
        this.rgbService = rgbService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<JSONDisplayObject> getAllObjects() {
        System.out.println("HEY");
        rgbService.getJsonDisplayObjects().collect(Collectors.toList()).forEach(jsonDisplayObject -> {
            try {
                System.out.println(new ObjectMapper().writeValueAsString(jsonDisplayObject));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return rgbService.getJsonDisplayObjects().collect(Collectors.toList());
    }
}
