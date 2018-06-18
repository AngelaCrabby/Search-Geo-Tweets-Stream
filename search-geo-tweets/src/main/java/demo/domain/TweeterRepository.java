package demo.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TweeterRepository extends PagingAndSortingRepository<Tweeter, String> {
    List<Tweeter> findByPointNear(Point point, Distance distance);

    List<Tweeter> findByPointNear(Point point, Distance distance, Sort sort);
}
