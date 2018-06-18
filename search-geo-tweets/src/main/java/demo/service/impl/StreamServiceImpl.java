package demo.service.impl;

import demo.domain.Tweeter;
import demo.service.StreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            tweeter.setPoint(new Point(position.get(0), position.get(1)));
        } else { // Geo = mid point of its polygon
            List<List<Double>> points = tweeter.getPlace().getBounding_box().getCoordinates().get(0);

            for (List<Double> point : points) {
                longitude += point.get(0);
                latitude += point.get(1);
            }
            longitude = longitude / 4;
            latitude = latitude / 4;

            tweeter.setPoint(new Point(doubleFormat(longitude), doubleFormat(latitude)));
        }

    }

    // Keep six decimal places and round half up for GeoPoint
    private Double doubleFormat(Double num) {
        BigDecimal bigDecimal = new BigDecimal(num);
        return bigDecimal.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
