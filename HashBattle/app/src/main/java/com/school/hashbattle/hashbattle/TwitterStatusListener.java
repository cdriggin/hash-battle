package com.school.hashbattle.hashbattle;

import android.os.AsyncTask;
import android.util.Log;

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
        this.healthBarOne = healthBarOne;
        this.healthBarTwo = healthBarTwo;
        this.hashTagOne = hashTagOne;
        this.hashTagTwo = hashTagTwo;
        initializeTwitterListener(hashTagOne, hashTagTwo);
    }

    private void initializeTwitterListener(String hashTagOne, String hashTagTwo) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("")
                .setOAuthConsumerSecret("")
                .setOAuthAccessToken("")
                .setOAuthAccessTokenSecret("");

        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(cb.build());

        stream = twitterStreamFactory.getInstance();
        stream.addListener(this);
        String[] trackArray = new String[]{
                hashTagOne,
                hashTagTwo
        };
        stream.filter(new FilterQuery(trackArray));
    }

    @Override
    public void onStatus(Status status) {
        HashtagEntity[] tags = status.getHashtagEntities();
        for (int i = 0; i < tags.length; i++) {
            if (tags[i].getText().toUpperCase().contains(hashTagOne.toUpperCase().replace("#", ""))) {
                healthBarOne.takeHit();
            } else {
                healthBarTwo.takeHit();
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
        stream.cleanUp();
        stream.shutdown();
    }
}
