package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.twitter.api.GeoCode;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Twitter REST api - search example
 * !!! All the method under this controller, return json or String
 */
@RestController
public class TweetsFetchController {

    private Twitter twitter;

    @Autowired
    public TweetsFetchController(Twitter twitter) {
        this.twitter = twitter;
    }

    // http://localhost:10000/tweets
    @RequestMapping(value = "/tweets", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Tweet> pullStream() throws InterruptedException {
        List<Tweet> tweets = twitter.searchOperations().search("artificial intelligence", 3).getTweets();
        return tweets;
    }

    // http://localhost:10000/tweets/latitude=37.774929,longitude=-122.419416
    @RequestMapping(value = "/tweets/latitude={latit},longitude={longi}", method = RequestMethod.GET)
    public List<Tweet> upload(@PathVariable(value = "latit") String latit,
                              @PathVariable(value = "longi") String longi) {
        String Query = "Artifical Intelligence";
        SearchParameters params = new SearchParameters(Query);
        double latitude = Double.parseDouble(latit);
        double longitude = Double.parseDouble(longi);
        int radius = 100;
        params.geoCode(new GeoCode(latitude, longitude, radius));
        //params.resultType(SearchParameters.ResultType.RECENT);
        params.count(5);

        List<Tweet> tweets = twitter.searchOperations().search(params).getTweets();

        return tweets;
    }


}
