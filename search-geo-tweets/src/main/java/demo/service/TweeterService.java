package demo.service;

import demo.domain.Tweeter;

import java.util.List;

public interface TweeterService {
    List<Tweeter> filterTextByKeyword(List<Tweeter> tweeters, String keyword);
}
