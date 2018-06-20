# Search-Geo-Tweets-Stream

## Feature:
~~~
  1. Python + Twitter Streaming Api [Support San Francisco & New York Areas Real Time Tweets] ---> 
  2. RabbitMQ ---> 
  3. Java + MongoDB (GeoSpacial + Text Index & Query) + 
     Spring Boot REST api [ Top250 Tweets(time), based on NearGeoText{[lat/long], radius(mile), keyword} ]
~~~
## How to Start Search-Geo-Tweets-Stream
~~~
  1. Terminal 1: [Docker]
         docker-compose up
     Result:  rabbitMQ port sets up & mongoDB port sets up
  2. Terminal 2: [Python]
         cd ../python
     --> python streaming.py
     Result:  streaming tweets, one by one,
              e.g: {"created_at": ..., "timestamp_ms": ...}
  3. Terminal 3: [Java Search Geo Tweets]
         cd ../search-geo-tweets/target
     --> java -jar search-geo-tweets-1.0.0.BUILD-SNAPSHOT.jar 
     Result:  Tomcat started on port: 10000,
              consuming tweets & storing them into MongoDB, one by one,
              e.g: Received tweet {"created_at": ..., "timestamp_ms": ...}
                   Saved to MongoDB Tweeter Tweeter(id= ..., point=Point ...}
~~~
## How to Run Search-Geo-Tweets-Stream
~~~
  0. ctrl + C to terminate Terminal [Python], a easy way to check the correctness of this result by eyes.
     (Leave this step, if you want to see the real-time result ^0^~)
     
  1. search all geoTweets in MongoDB
     Chrome:    URL:  http://localhost:10000/all   ---> Go!
     Response:  JsonList:  [Recommend:  Json Formatter - Chrome extension to see the Pretty Json Result ^0^~]
                e.g: [{"timestamp_ms": ..., "latitude": ...}, ..., {...}]
                
  2. geo-spacial search Top 250 GeoTweets, Within/Near latitude/longitude, radius
     Chrome:    URL:  http://localhost:10000/latitude=37.338208,longitude=-122.019416,radius=70   ---> Go!
                      latitude/longitude : You may adjust them, based on the result of (1) all geoTweets;
                                           [GeoLocation]-file name:  a reference to change latitude/longitude
                      radius:              You may change to 25, (Unit: mile)
     Response:  JsonList:
                e.g: [{"timestamp_ms": ..., "latitude": ...}, ..., {...}]
 
  3. text indexes search GeoTweets, based on keywords  ---  Text Indexes on {Text, Entities.HashTags.Text}; 
                                                       ---  Search {keyword1 OR keyword2 OR ...}
     Chrome:    URL:  http://localhost:10000/keyword=Rafa Nadal AI
     Response:  JsonList:
                e.g: [{"timestamp_ms": ..., "latitude": ...}, ..., {...}]
                
  4. geo-spacial + text search Top 250 GeoTweets, based on lat/long, radius, keyword
     Chrome:    URL:  http://localhost:10000/geoTweet/latitude=37.338208,longitude=-122.019416,radius=70,keyword=AI
                      keyword:             You may adjust it, based on the result of (2) geo & (3) text
     Response:  JsonList:
                e.g: [{"timestamp_ms": ..., "latitude": ...}, ..., {...}]  
  
  5. delete all geoTweets in MongoDB  -- [option]
     Postman:   [DELETE]  -->  URL:  http://localhost:10000/purge   -->   Send
     Response:  200 OK
                You may use (1) all geoTweets to double check
~~~
