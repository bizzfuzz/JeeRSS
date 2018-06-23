/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeerss;

import com.sun.xml.internal.fastinfoset.stax.events.Util;
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
import ui.FetchDispatch;

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
    public Thread fetchThread;
    
    public Session()
    {
        shared = this;
    }
    
    public void start()
    {
        //System.out.println("session start");
        init();
        //urls.add("https://www.gamingonlinux.com/article_rss.php");
        //urls.add("https://www.wired.com/feed/rss");
        //urls.add("https://www.theguardian.com/uk/sport/rss");
        urls.add("https://stackoverflow.com/jobs/feed");
        dispatchFetch();
    }
    private void init()
    {
        new JFXPanel();//workaround for toolkit not initialized
        feeds = new LinkedList<>();
        urls = new LinkedList<>();
        saved = new RSS();
        saved.setTitle("Saved Stories");
        saved.savedstory = true;
    }
    public void dispatchFetch()
    {
        fetchThread = new Thread(new FetchDispatch(this));
        fetchThread.start();
    }
    public void fetchFeeds() throws IOException
    {
        for(String url : urls)
                addFeed(url);
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
    public void load(String savestring) throws IOException
    {
        init();
        String[] split = savestring.split("</feeds>");
        String links = split[0];
        links = links.split("<feeds>")[1];
        System.out.println("-------------------------");
        for(String line : links.split("\n"))
        {
            line = line.trim();
            if(!Util.isEmptyString(line))
            {
                System.out.println(line);//load rss's here
                addFeed(line);
            }
        }
        System.out.println("-----------/urls--------------");
        String stories = split[1];
        String delim = "</article>";
        String title, url, content, date, article;
        Story storyobj;
        for(String story : stories.split("<story>"))
        {
            System.out.println("------------story-------------");
            if(story.contains(delim))
            {
                title = story.split("<title>")[1].split("</title>")[0];//get text between tags
                url = story.split("<url>")[1].split("</url>")[0];
                content = story.split("<content>")[1].split("</content>")[0];
                date = story.split("<date>")[1].split("</date>")[0];
                article = story.split("<article>")[1].split("</article>")[0];
                
                storyobj = new Story();
                storyobj.title = title;
                storyobj.url = url;
                storyobj.content = content;
                storyobj.date = date;
                storyobj.article = article;
                saved.addStory(storyobj);
                System.out.println(url);
            }
            System.out.println("---------------/story----------");
        }
        showfeeds();
        //System.out.println(links);
    }
    public void addFeed(String url) throws IOException
    {
        //System.out.println("Adding feed: " + url);
        RSS added = new RSS(url);
        feeds.add(added);
        //System.out.println("Added feed: " + added);
    }
    public void refreshfeeds() throws InterruptedException
    {
        mainController.clearFeeds();
        dispatchFetch();
        fetchThread.join();
        showfeeds();
    }
    public void showfeed(RSS rss)
    {
        Platform.runLater(() ->
        {
            System.out.println(mainController);
            //System.out.println("sfeed: " + rss);
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
