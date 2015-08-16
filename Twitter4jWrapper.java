package com.jacobamason.FTCRC_Extensions;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Jacob on 8/15/2015.
 */
public class Twitter4jWrapper
{
    public void updateStatus(String latestStatus)
    {
        if (latestStatus.length() < 1) {
            System.out.println("Must give a status to tweet");
            return;
        }
        Twitter twitter = new TwitterFactory().getInstance();
        Status status = null;
        try
        {
            status = twitter.updateStatus(latestStatus);
            System.out.println("Successfully updated the status to [" + status.getText() + "].");
        } catch (TwitterException e)
        {
            e.printStackTrace();
        }
    }
}
