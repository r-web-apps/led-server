package me.rubeen.arduino.led.server;

import me.rubeen.arduino.led.server.entities.IShiningObject;
import me.rubeen.arduino.led.server.entities.LightChain;
import me.rubeen.arduino.led.server.entities.RGBStripe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import static me.rubeen.arduino.led.server.entities.IShiningObject.LightState.OFF;
import static me.rubeen.arduino.led.server.entities.IShiningObject.LightState.ON;
import static me.rubeen.arduino.led.server.entities.RGBStripe.LightColor.*;

@RequestMapping(path = "/set")
@Component
public class SetController {
    private final ServiceRGB rgbService;
    private final ServiceLightChain lightChainService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SetController(ServiceRGB rgbService, ServiceLightChain lightChainService) {
        this.rgbService = rgbService;
        this.lightChainService = lightChainService;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, params = "state")
    public ResponseEntity<String> allLeds(@RequestParam("state") Integer state) {
        LOG.info("Setting all LED's: {}", state > 0 ? "on" : "off");
        lightChainService.getLightChains().forEach(lightChain -> lightChain.turn(state > 0 ? ON : OFF));
        rgbService.getRgbStripes().forEach(rgbStripe -> rgbStripe.turn(state > 0 ? ON : OFF));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/off/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> setOffForId(@PathVariable int id) {
        IShiningObject shiningObject;
        shiningObject = rgbService.getStripe(id);
        if (shiningObject == null)
            shiningObject = lightChainService.getChain(id);
        if (shiningObject == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        shiningObject.turn(OFF);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/manual/{id}")
    public ResponseEntity<String> setManualColorValue(@RequestHeader("color") final String color,
                                                      @RequestHeader("brightness") final Integer brightness,
                                                      @PathVariable final Integer id) {
        LOG.info(color + " should be set to " + brightness);
        RGBStripe.LightColor lightColor = null;
        switch (color) {
            case "red":
                lightColor = RED;
                break;
            case "green":
                lightColor = GREEN;
                break;
            case "blue":
                lightColor = BLUE;
                break;
        }
        rgbService.getStripe(id).set(lightColor, brightness);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/auto/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> setAutomaticForId(@PathVariable int id) {
        RGBStripe stripe;
        LightChain lightChain;
        if ((stripe = rgbService.getStripe(id)) != null) {
            stripe.setAutoEnabled(!stripe.getAutoEnabled());
            stripe.set(RED);
            stripe.set(GREEN);
            stripe.set(BLUE);
            return new ResponseEntity<>(HttpStatus.OK);
        } else if ((lightChain = lightChainService.getChain(id)) != null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        } else {
            LOG.error("Unable to get a light for id: ", id);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
