package demo.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TweeterRepository extends PagingAndSortingRepository<Tweeter, String> {
    //List<Tweeter> findCircleNear(GeoPoint geoPoint );
}
