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
  1. Terminal 1:
         docker-compose up
     Result:  rabbitMQ port sets up & mongoDB port sets up
  2. Terminal 2:
         cd ../python
     --> python streaming.py
     Result:  streaming tweets, one by one
  3. Terminal 3:
         cd ../search-geo-tweets/target
     --> java -jar search-geo-tweets-1.0.0.BUILD-SNAPSHOT.jar 
     Result:  Tomcat started on port: 10000,
              consuming tweets, one by one
~~~
