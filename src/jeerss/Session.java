/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeerss;

import rss.RSS;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import rss.Story;
import ui.FXMLController;

/**
 *
 * @author ian
 */
public class Session
{
    public static Session shared;
    LinkedList<String> urls;
    LinkedList<RSS> feeds;
    public FXMLController mainController;
    RSS saved;
    
    public Session()
    {
        shared = this;
    }
    
    public void start()
    {
        try
        {
            feeds = new LinkedList<>();
            urls = new LinkedList<>();
            urls.add("https://www.gamingonlinux.com/article_rss.php");
            //urls.add("https://www.wired.com/feed/rss");
            urls.add("https://www.theguardian.com/uk/sport/rss");
            //urls.add("https://stackoverflow.com/jobs/feed");
            for(String url : urls)
                feeds.add(new RSS(url));
            saved = new RSS();
            saved.setTitle("Saved Stories");
            saved.savedstory = true;
            feeds.add(saved);
            showfeeds();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showfeed(RSS rss)
    {
        Platform.runLater(() ->
        {
            System.out.println(mainController);
            mainController.populateFeed(rss);
        });
    }
    public void showfeeds()
    {
        showStatus("Fetching feeds");
        Platform.runLater(() ->
        {
            feeds.forEach((feed) ->
            {
                showfeed(feed);
            });
            showStatus("Fetch complete");
        });
    }
    public Story getStory(int feed, int story)
    {
        return feeds.get(feed).getStory(story);
    }
    public void showStatus(String text)
    {
        Platform.runLater(() ->
        {
            mainController.setStatus(text);
        });
    }
    public void saveStory(Story story)
    {
        saved.addStory(story);
    }
}
