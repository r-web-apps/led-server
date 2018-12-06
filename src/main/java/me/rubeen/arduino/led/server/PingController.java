package me.rubeen.arduino.led.server;

import me.rubeen.arduino.led.server.entities.IShiningObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@RequestMapping
@Component
public class PingController {
    private ServiceRGB serviceRGB;
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    public PingController(ServiceRGB serviceRGB) {
        this.serviceRGB = serviceRGB;
    }

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public ResponseEntity<String> ping(HttpServletRequest request) {
        LOG.info("got PING from {}", request.getRemoteAddr());
        return new ResponseEntity<>("PONG", HttpStatus.OK);
    }
}
