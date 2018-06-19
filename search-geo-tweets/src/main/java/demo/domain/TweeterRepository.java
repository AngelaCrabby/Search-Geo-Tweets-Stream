package demo.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TweeterRepository extends MongoRepository<Tweeter, String> {
    // Geo-Special Index & Query
    List<Tweeter> findByPointNear(Point point, Distance distance, Sort sort, LimitOperation limit);

    // Text Index & Query
    List<Tweeter> findAllBy(TextCriteria textCriteria);

    // Text + Geo Query not workable
    //List<Tweeter> findByLocationWithin(Circle circle, TextCriteria textCriteria);

}
