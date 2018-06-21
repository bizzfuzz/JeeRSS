/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jeerss.Session;

/**
 *
 * @author ian
 */
public class FetchDispatch implements Runnable
{
    Session session;
    
    public FetchDispatch(Session s)
    {
        session = s;
    }
    @Override
    public void run()
    {
        try
        {
            session.fetchFeeds();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(FetchDispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
