from __future__ import absolute_import, print_function

from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream

import pika   # for RabbitMQ

# Go to http://apps.twitter.com and create an app.
# The consumer key and secret will be generated for you after
consumer_key = "Eu44hVEUq1GX73HxI1uyEzGV1"
consumer_secret = "QcHENWTwLxCrBQPaceZhu3djp6YRUr2pGPNqA1sYF8mXFXlbSd"

# After the step above, you will be redirected to your app's page.
# Create an access token under the the "Your access token" section
access_token = "1004844321252634624-qRZdVLNYomSDyf1xVT8IUgWqYXKcyn"
access_token_secret = "tz23XHfxJz1yOVGGp0F9nL3CHpmSOvv6tL6uAcCudx2DA"


class StdOutListener(StreamListener):
    """ A listener handles tweets that are received from the stream.
    This is a basic listener that just prints received tweets to stdout.
    """

    def __init__(self, api=None):
        # initialise the Twitter stream
        super(StdOutListener, self).__init__()

        self.rabbitMQconnection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
        self.channel = self.rabbitMQconnection.channel()
        self.channel.queue_declare(queue='binder.hello', durable=True)

    def on_data(self, data):
        print(data)
        # send the tweet into the message queue
        self.channel.basic_publish(exchange='',
                                   routing_key='binder.hello',
                                   body=data)
        return True

    def on_error(self, status):
        print(status)


if __name__ == '__main__':
    listener = StdOutListener()
    auth = OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_token_secret)

    stream = Stream(auth, listener)
    #stream.filter(track=['basketball'])   # filter by keyword

    """ filter stream tweets by San Francisco OR New York City
        -122.75,36.8,-121.75,37.8 --> San Francisco
        -74,40,-73,41             --> New York City
    """
    stream.filter(locations=[-122.75,36.8,-121.75,37.8,-74,40,-73,41])
