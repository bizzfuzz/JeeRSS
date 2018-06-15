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

/**
 *
 * @author ian
 */
public class Session
{
    
    public Session()
    {
        try
        {
            String url = "https://www.gamingonlinux.com/article_rss.php";
            RSS rss = new RSS(url);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
