package demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/** !!! All the method under this controller, must return a dynamic page*/
@Controller   // for dynamic page return
@Slf4j
public class HelloController {

    private Twitter twitter;
    private ConnectionRepository connectionRepository;

    @Autowired
    public HelloController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    // http://localhost:10000
    @RequestMapping(value = "/", method= RequestMethod.GET)
    public String helloTwitter(Model model) {
//        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
//            // http://localhost:10000/connect/twitter
//            return "redirect:/connect/twitter";  // twitterConnect.html
//        }
        //List<Tweet> tweets = twitter.timelineOperations().getUserTimeline("AngelaCrabby");

        model.addAttribute(twitter.userOperations().getUserProfile("AngelaCrabby"));
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends("AngelaCrabby");
        model.addAttribute("friends", friends);
        return "hello"; // ../templates/hello.html
    }

    // http://localhost:10000/list
    @RequestMapping(value = "/list", method= RequestMethod.GET)
    public String helloTwitter2(Model model) {
        List<Tweet> tweets = twitter.searchOperations().search("artificial intelligence", 3).getTweets();

        String tweetText = tweets.get(0).getText();
        model.addAttribute("tweets", tweets);
        return "hello2";
    }

}
