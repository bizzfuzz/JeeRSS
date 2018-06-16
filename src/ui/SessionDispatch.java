/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import jeerss.Session;

/**
 *
 * @author ian
 */
public class SessionDispatch implements Runnable
{
    public final Session sess;
    public SessionDispatch(Session s)
    {
        sess=s;
    }
    
    @Override
    public void run()
    {
        sess.start();
    }
}
