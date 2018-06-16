/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeerss;

import RSS.RSS;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import ui.FXMLController;

/**
 *
 * @author ian
 */
public class Session
{
    public static Session shared;
    
    public Session()
    {
        shared = this;
    }
    
    public void start()
    {
        try
        {
            String url = "https://www.gamingonlinux.com/article_rss.php";
            RSS rss = new RSS(url);
            showfeed(rss);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void showfeed(RSS feed)
    {
        FXMLController controller = FXMLController.shared;
        Platform.runLater(() ->
        {
            controller.populateFeed(feed.feed);
        } // ...
        );
    }
}
