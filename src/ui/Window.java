/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jeerss.Session;

/**
 *
 * @author ian
 */
public class Window extends Application
{
    
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("JeeRSS.fxml"));
        primaryStage.setTitle("JeeRSS");
        Scene scene = new Scene(root, 640, 700);
        scene.getStylesheets().add(
            getClass().getResource("fxml.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Thread thread = new Thread(new SessionDispatch(Session.shared));
        //thread.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
