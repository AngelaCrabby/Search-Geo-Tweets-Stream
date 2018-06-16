package demo.controller;

import demo.service.StreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller   // for dynamic page return
@Slf4j
public class TweetsStreamController {

    private StreamService streamService;

    @Autowired
    public TweetsStreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    // http://localhost:10000/stream/10000
    @RequestMapping(value = "/stream/{time}")
    public String streamTweet(@PathVariable(value = "time") int time, Model model) throws InterruptedException{
        Model returnedmodel = streamService.streamApi(model, time);
        model = returnedmodel; // ?

        return "stream";
    }
}
