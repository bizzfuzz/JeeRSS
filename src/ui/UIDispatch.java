/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

/**
 *
 * @author ian
 */
public class UIDispatch implements Runnable
{
    @Override
    public void run()
    {
        javafx.application.Application.launch(Window.class);
    }
}
