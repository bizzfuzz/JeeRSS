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
import ui.FXMLController;
import ui.Window;

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
    
    public Session()
    {
        shared = this;
    }
    
    public void start()
    {
        try
        {
            //mainController = Window.loader.getController();
            System.out.println("\t\t\tin");
            feeds = new LinkedList<>();
            urls = new LinkedList<>();
            urls.add("https://www.gamingonlinux.com/article_rss.php");
            urls.add("https://www.wired.com/feed/rss");
            //urls.add("https://www.theguardian.com/uk/sport/rss");
            for(String url : urls)
                feeds.add(new RSS(url));
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
        System.out.println("feed:" + feeds);
        feeds.forEach((feed) ->
        {
            showfeed(feed);
        });
    }
}
