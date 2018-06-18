package demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.domain.Tweeter;
import demo.domain.TweeterRepository;
import demo.service.StreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@EnableBinding(Sink.class)  // bind MQ Consumer channel with this class
@Slf4j // log
public class StreamTweetSink {

    @Autowired
    private ObjectMapper objectMapper; // change JSON into Tweeter class

    @Autowired
    private StreamService streamService;

    @Autowired
    private TweeterRepository repository;

    @ServiceActivator(inputChannel = Sink.INPUT)  // handle input message from MQ
    public void receiveTweet(byte[] bytes) throws IOException {
        String input = new String(bytes, StandardCharsets.UTF_8);
        log.info("Received tweet " + input);

        // change input into Tweeter class
        Tweeter tweeter = this.objectMapper.readValue(input, Tweeter.class);
        streamService.updateGeoPoint(tweeter);

        //log.info("Tweeter " + tweeter.toString());
        log.info("Saved to MongoDB Tweeter" + repository.save(tweeter).toString());
    }
}
