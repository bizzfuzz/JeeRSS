/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeerss;

import ui.SessionDispatch;
import ui.UIDispatch;

/**
 *
 * @author ian
 */
public class JeeRSS
{
    static Session session;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        newinit();
    }
    
    public static void newinit()
    {
        Thread uithread = new Thread(new UIDispatch());
        session = new Session();
        Thread sessthread = new Thread(new SessionDispatch(session));
        
        uithread.start();
        sessthread.start();
    }
}
