/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jeerss.Session;

/**
 *
 * @author ian
 */
public class Window extends Application
{
    public static FXMLLoader loader;
    public static FXMLController controller;
    
    @Override
    @FXML
    public void start(Stage primaryStage) throws IOException
    {
        loader = new FXMLLoader(getClass().getResource("JeeRSS.fxml"));
        loader.setLocation(getClass().getResource("JeeRSS.fxml"));
        controller = new FXMLController();
        loader.setController(controller);
        Parent root = loader.load();
        //System.out.println("Controller: " + controller);
        primaryStage.setTitle("JeeRSS");
        Scene scene = new Scene(root, 900, 800);
        scene.getStylesheets().add(
            getClass().getResource("fxml.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.setOnShowing((WindowEvent event) ->
        {
            //System.out.println("Controller: " + controller);
            Session.shared.mainController = controller;
            Session.shared.showfeeds();
        });
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
