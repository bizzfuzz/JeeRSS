/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeerss;

import ui.SessionDispatch;
import ui.Window;

/**
 *
 * @author ian
 */
public class JeeRSS
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Session session = new Session();
        Thread thread = new Thread(new SessionDispatch(Session.shared));
        thread.start();
        javafx.application.Application.launch(Window.class);
    }
}
