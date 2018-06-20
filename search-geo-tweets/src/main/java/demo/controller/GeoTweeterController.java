package demo.controller;

import demo.domain.Tweeter;
import demo.domain.TweeterRepository;
import demo.service.TweeterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class GeoTweeterController {

    private TweeterRepository tweeterRepository;
    private TweeterService tweeterService;

    @Autowired
    public GeoTweeterController(TweeterRepository tweeterRepository, TweeterService tweeterService) {
        this.tweeterRepository = tweeterRepository;
        this.tweeterService = tweeterService;
    }

    // http://localhost:10000/all
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Tweeter> findAll() {
        return (List<Tweeter>) tweeterRepository.findAll();
    }

    // Postman, Delete : http://localhost:10000/purge
    @RequestMapping(value = "/purge", method = RequestMethod.DELETE)
    public void deleteAll() {
        tweeterRepository.deleteAll();
    }

    // http://localhost:10000/latitude=37.338208,longitude=-122.019416,radius=70
    @RequestMapping(value = "/latitude={latitude},longitude={longitude},radius={radius}", method = RequestMethod.GET)
    public List<Tweeter> searchTop250Tweets(@PathVariable(value = "latitude") double latitude,
                                            @PathVariable(value = "longitude") double longitude,
                                            @PathVariable(value = "radius") int radius) {
        Point point = new Point(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.MILES);
        Sort sort = new Sort(Sort.Direction.DESC, "timestamp_ms");
        LimitOperation limit = new LimitOperation(250);

        return tweeterRepository.findByPointNear(point, distance, sort, limit);
    }

    // http://localhost:10000/keyword=immigrant dog
    @RequestMapping(value = "/keyword={keyword}", method = RequestMethod.GET)
    public List<Tweeter> searchByKeyword(@PathVariable(value = "keyword") String keyword) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(keyword);

        return tweeterRepository.findAllBy(criteria);
    }

    // http://localhost:10000/geoTweet/latitude=37.338208,longitude=-122.019416,radius=70,keyword=dog
    @RequestMapping(value = "/geoTweet/latitude={latitude},longitude={longitude},radius={radius},keyword={keyword}", method = RequestMethod.GET)
    public List<Tweeter> searchGeoAndText(@PathVariable(value = "latitude") double latitude,
                                          @PathVariable(value = "longitude") double longitude,
                                          @PathVariable(value = "radius") int radius,
                                          @PathVariable(value = "keyword") String keyword) {

        log.info("geo & test: " + longitude + " " + latitude + " " + radius + " " + keyword);

        Point center = new Point(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.MILES);
        Circle circle = new Circle(center, distance);
        Sort sort = new Sort(Sort.Direction.DESC, "timestamp_ms");
        LimitOperation limit = new LimitOperation(250);
//        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(keyword);
//        tweeterRepository.findByLocationWithin(circle, criteria); // --- not workable

        List<Tweeter> tweeters = tweeterRepository.findByPointNear(center, distance, sort, limit);
        return tweeterService.filterTextByKeyword(tweeters, keyword);
    }

}
