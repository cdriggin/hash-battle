package com.school.hashbattle.hashbattle;

import android.os.AsyncTask;
import android.util.Log;

import twitter4j.FilterQuery;
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
public class TwitterStatusListener implements StatusListener{
    private TwitterStream stream;
    private HealthBar healthBar;

    public TwitterStatusListener(HealthBar healthBar, String hashTag) {
        this.healthBar = healthBar;
        initializeTwitterListener(hashTag);
    }

    private void initializeTwitterListener(String hashTag) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("5EHdC7mTApXrx8cb61hcKsOWc")
                .setOAuthConsumerSecret("JuR4WCdh7sfYInZDOsSYA5Dnkfn5IH7oRdW0P1lD6BTkxaGiKG")
                .setOAuthAccessToken("94453551-YG4BZdJhWKWOHVRc0z2GMby1zuo04ACccskPqv1ia")
                .setOAuthAccessTokenSecret("jM6NrPF040SYZ22m40aUwdbTDFFisEipK8eo8Y6SKxiof");

        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(cb.build());

        stream = twitterStreamFactory.getInstance();
        stream.addListener(this);
        String[] trackArray = new String[] {
                hashTag
        };
        stream.filter(new FilterQuery(trackArray));
    }

    @Override
    public void onStatus(Status status) {
        healthBar.takeHit();
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
        new Runnable() {
            @Override
            public void run() {
                stream.shutdown();
            }
        }.run();
    }
}
