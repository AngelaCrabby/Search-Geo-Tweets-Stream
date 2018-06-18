package demo.service.impl;

import demo.domain.GeoPoint;
import demo.domain.Tweeter;
import demo.service.StreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StreamServiceImpl implements StreamService {

    @Override
    public void updateGeoPoint(Tweeter tweeter) {
        double longitude = 0.0;
        double latitude = 0.0;

        if (tweeter.getCoordinates() != null) { // hasGeo
            List<Double> position = tweeter.getCoordinates().getCoordinates();
            tweeter.setGeoPoint(new GeoPoint(position.get(0), position.get(1)));
        } else { // Geo = mid point of its polygon
            List<List<Double>> points = tweeter.getPlace().getBounding_box().getCoordinates().get(0);
            int decimal = 1000000;  // for decimal format Double

            for (List<Double> point : points) {
                longitude += point.get(0);
                latitude += point.get(1);
            }
            longitude = Double.valueOf(Math.round(longitude * decimal) / (4 * decimal));
            latitude = Double.valueOf(Math.round(latitude * decimal) / (4 * decimal));

            tweeter.setGeoPoint(new GeoPoint(longitude, latitude));
        }

    }

}
