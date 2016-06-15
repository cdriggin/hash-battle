package com.school.hashbattle.hashbattle;

import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by nathanflint on 6/11/16.
 */
public class TwitterStatusListener implements StatusListener {
    private TwitterStream stream;
    private HealthBar healthBarOne;
    private HealthBar healthBarTwo;
    private String hashTagOne;
    private String hashTagTwo;

    public TwitterStatusListener(HealthBar healthBarOne, HealthBar healthBarTwo, String hashTagOne, String hashTagTwo) {
        saveMembers(healthBarOne, healthBarTwo, hashTagOne, hashTagTwo);
        stream = buildStream();
        startListening(hashTagOne, hashTagTwo);
    }

    private void saveMembers(HealthBar healthBarOne, HealthBar healthBarTwo, String hashTagOne, String hashTagTwo) {
        this.healthBarOne = healthBarOne;
        this.healthBarTwo = healthBarTwo;
        this.hashTagOne = hashTagOne;
        this.hashTagTwo = hashTagTwo;
    }

    private TwitterStream buildStream() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("wurmcknqhGNfHE138XvwlWmzx")
                .setOAuthConsumerSecret("9PbFVysxaSmC8nogT8pS4sjVJsLiHHRHu0XGyT7ZquilAeYadr")
                .setOAuthAccessToken("94453551-ymfrwAy1EU1FB8rxZWYAXGiLMQVhQbQh2KJfiB309")
                .setOAuthAccessTokenSecret("5nrKCgSl55E6edoKvUSz8fxyzJq5rJTC1dA7rdJRSRx7D");

        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(cb.build());

        return twitterStreamFactory.getInstance();
    }

    private void startListening(String hashTagOne, String hashTagTwo) {
        stream.addListener(this);
        String[] trackArray = new String[]{
                hashTagOne,
                hashTagTwo
        };
        stream.filter(new FilterQuery(trackArray));
    }


    public void reinitialize(HealthBar healthBarOne, HealthBar healthBarTwo, String hashTagOne, String hashTagTwo) {
        saveMembers(healthBarOne, healthBarTwo, hashTagOne, hashTagTwo);
        startListening(hashTagOne, hashTagTwo);
    }

    @Override
    public void onStatus(Status status) {
        HashtagEntity[] tags = status.getHashtagEntities();
        for (int i = 0; i < tags.length; i++) {
            if (tags[i].getText().toUpperCase().contains(hashTagOne.toUpperCase().replace("#", ""))) {
                healthBarTwo.takeHit();
            } else {
                healthBarOne.takeHit();
            }
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        System.out.println("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }

    public void stop() {
        stream.clearListeners();
    }
}
