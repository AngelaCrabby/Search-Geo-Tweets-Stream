package demo.service.impl;

import demo.domain.Tweeter;
import demo.service.TweeterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweeterServiceImpl implements TweeterService {

    @Override
    public List<Tweeter> filterTextByKeyword(List<Tweeter> tweeters, String keyword) {
        List<Tweeter> result = new ArrayList<>();

        for (Tweeter tweeter : tweeters) {
            if (tweeter.getText().contains(keyword)) {
                result.add(tweeter);
            }
        }

        return result;
    }
}
