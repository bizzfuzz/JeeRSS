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
import javafx.embed.swing.JFXPanel;
import rss.Story;
import scrape.Prefs;
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
    Prefs set;
    
    public Session()
    {
        shared = this;
    }
    
    public void start()
    {
        System.out.println("session start");
        try
        {
            new JFXPanel();//workaround for toolkit not initialized
            feeds = new LinkedList<>();
            urls = new LinkedList<>();
            //urls.add("https://www.gamingonlinux.com/article_rss.php");
            //urls.add("https://www.wired.com/feed/rss");
            //urls.add("https://www.theguardian.com/uk/sport/rss");
            //urls.add("https://stackoverflow.com/jobs/feed");
            for(String url : urls)
                addFeed(url);
            saved = new RSS();
            saved.setTitle("Saved Stories");
            saved.savedstory = true;
        }
        catch (IOException ex)
        {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String savestring()
    {
        return saveFeeds();
    }
    public String saveFeeds()
    {
        String ret = "<feeds>\n";
        ret = feeds.stream().map((feed) -> "\t" + feed.url + "\n").reduce(ret, String::concat);
        ret += "</feeds>";
        ret += "\n<saved>\n";
        ret = saved.feed.stream().map((story) -> story.saveString() + "\n").reduce(ret, String::concat);
        ret += "</saved>";
        return ret;
    }
    public void addFeed(String url) throws IOException
    {
        feeds.add(new RSS(url));
    }
    public void refreshfeeds()
    {
        mainController.clearFeeds();
        showfeeds();
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
            mainController.clearFeeds();
            feeds.forEach((feed) ->
            {
                showfeed(feed);
            });
            showfeed(saved);
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
        if(story.saved)
            return;
        saved.addStory(story);
        story.saved = true;
    }
}
