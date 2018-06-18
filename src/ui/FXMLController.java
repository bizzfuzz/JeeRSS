/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.IOException;
import rss.Story;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import rss.RSS;

/**
 * FXML Controller class
 *
 * @author ian
 */
public class FXMLController implements Initializable
{
    @FXML
    VBox menuitems;
    @FXML
    ScrollPane menuscroll;
    @FXML
    VBox article;
    @FXML
    Label title;
    @FXML
    WebView view;
    WebEngine engine;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        menuscroll.setHbarPolicy(ScrollBarPolicy.NEVER);
        menuscroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        title.setAlignment(Pos.CENTER_LEFT);
        title.setMaxWidth(50);
        title.setMaxHeight(100);
        title.setWrapText(true);
        article.setPadding(new Insets(10, 0,0,0));
        engine = view.getEngine();
    }
    public void populateFeed(RSS rss)
    {
        StoryButton button;
        ObservableList<Node> menu = menuitems.getChildren();
        Label feedtitle = new Label(rss.title);
        feedtitle.getStyleClass().add("lighttext");
        menu.add(feedtitle);
        Story story;
        for(int i = 0; i < rss.feed.size(); i++)
        {
            story = rss.getStory(i);
            button = new StoryButton(story, this);
            menu.add(button);
        }
        //System.out.println(menuitems.getChildren().size());
    }
    public void showStory(Story story) throws IOException
    {
        title.setText(story.title);
        engine.loadContent(story.getArticle(), "text/html");
        
    }
}
