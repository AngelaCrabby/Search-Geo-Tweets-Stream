package demo.controller;

import demo.domain.Tweeter;
import demo.domain.TweeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeoTweeterController {

    @Autowired
    private TweeterRepository tweeterRepository;

    // http://localhost:10000/all
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Tweeter> findAll() {
        return (List<Tweeter>) tweeterRepository.findAll();
    }

    // http://localhost:10000/purge
    @RequestMapping(value = "/purge", method = RequestMethod.DELETE)
    public void deleteAll() {
        tweeterRepository.deleteAll();
    }

    // http://localhost:10000/latitude=37.774929,longitude=-122.419416,radius=25
    @RequestMapping(value = "/latitude={latitude},longitude={longitude},radius={radius}", method = RequestMethod.GET)
    public List<Tweeter> upload(@PathVariable(value = "latitude") double latitude,
                                @PathVariable(value = "longitude") double longitude,
                                @PathVariable(value = "radius") int radius) {
        Point point = new Point(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.MILES);

        return tweeterRepository.findByPointNear(point, distance);
    }
}
