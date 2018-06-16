/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import RSS.Story;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ian
 */
public class FXMLController implements Initializable
{
    @FXML
    VBox menuitems;
    public static FXMLController shared;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        shared = this;
    }    
    public void populateFeed(LinkedList<Story> feed)
    {
        Button button;
        for(Story story : feed)
        {
            button = new Button();
            button.setText(story.title);
            menuitems.getChildren().add(button);
        }
        System.out.println(menuitems.getChildren().size());
    }
}
