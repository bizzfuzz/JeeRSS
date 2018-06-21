/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author ian
 */
public class TextPopup
{
    Stage window;
    Button button;
    TextField field;
    public boolean complete = false;
    
    public TextPopup(String title)
    {
        setTitle(title);
        setupWindow();
    }
    private void setupWindow()
    {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
    }
    public String display(String title)
    {
            setTitle(title);
            return display();
    }
    public String display()
    {
        button = new Button("Ok");
        button.setOnAction((ActionEvent e) ->
        {
            complete = true;
            window.close();
        });
        VBox layout = new VBox();
        ObservableList<Node> items = layout.getChildren();
        field = new TextField();
        items.add(field);
        items.add(button);  
        Scene scene = new Scene(layout, 300, 50);
        window.setScene(scene);
        window.showAndWait();
        return entered();
    }
    private void setTitle(String title)
    {
        if(window == null)
            setupWindow();
        window.setTitle(title);
    }
     private String entered()
     {
         return field.getText();
     }
}
