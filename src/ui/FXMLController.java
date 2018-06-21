/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.File;
import java.io.IOException;
import rss.Story;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jeerss.Session;
import rss.RSS;
import scrape.Filer;
import scrape.Util;

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
    @FXML
    Label status;
    @FXML
    MenuItem savestory;
    @FXML
    MenuItem addfeed;
    @FXML
    MenuItem savesession;
    WebEngine engine;
    Story viewing;
    FeedButton savedstories;
    public Stage window;
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
        view.setContextMenuEnabled(false);
        view.setFontSmoothingType(FontSmoothingType.GRAY);
        setStatus("");
        setTitle("");
        
        savestory.setOnAction((ActionEvent t) -> 
        {
            saveStory();
        });
        addfeed.setOnAction((ActionEvent e) ->
        {
            try
            {
                TextPopup popup = new TextPopup("Add Feed");
                String newurl = popup.display();
                if(!Util.emptyString(newurl))
                {
                    Session.shared.addFeed(newurl);
                    Session.shared.refreshfeeds();
                }
            } 
            catch (IOException ex)
            {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        savesession.setOnAction((ActionEvent e) ->
        {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save Session");
            File file = chooser.showSaveDialog(window);
            if(file != null)
            {
                Filer filer = new Filer();
                filer.write(Session.shared.savestring(), file);
            }
        });
    }
    public void saveStory()
    {
        if(viewing.saved)
            return;
        if(viewing != null)
                Session.shared.saveStory(viewing);
        if(savedstories != null)
        {
            Platform.runLater(() ->
            {
                StoryButton copy = new StoryButton(viewing.button);
                savedstories.addStory(copy);
                menuitems.getChildren().add(copy);
                savedstories.showStories();
                setStatus("Saved: " + viewing.title);
                System.out.println("Saved len: " + savedstories.stories.size());
            });
        }
    }
    public void populateFeed(RSS rss)
    {
        StoryButton button;
        ObservableList<Node> menu = menuitems.getChildren();
        FeedButton head = new FeedButton(rss.title);
        head.setMinWidth(menuitems.getWidth());
        if(rss.savedstory)
            savedstories = head;
        menu.add(head);
        /*Label feedtitle = new Label(rss.title);
        feedtitle.setMinHeight(30);
        feedtitle.setPadding(new Insets(0,0,0, 10));
        feedtitle.getStyleClass().add("lighttext");
        menu.add(feedtitle);*/
        Story story;
        for(int i = 0; i < rss.feed.size(); i++)
        {
            story = rss.getStory(i);
            button = new StoryButton(story, this);
            button.getStyleClass().add("storybutton");
            button.setMinWidth(menuscroll.getWidth());
            story.button = button;
            menu.add(button);
            head.addStory(button);
        }
        //System.out.println(menuitems.getChildren().size());
    }
    public void showStory(Story story) throws IOException, InterruptedException
    {
        setTitle(story.title);
        String articletext = story.getArticle();
        //System.out.println(articletext);
        String html = "<a>" + story.url + "</a><br>" + articletext;
        engine.loadContent(html, "text/html");
        engine.setUserStyleSheetLocation(getClass().getResource("assets/article.css").toString());
        viewing = story;
    }
    public void setStatus(String text)
    {
        status.setText(text);
    }
    public void setTitle(String text)
    {
        title.setText(text);
    }
    public void clearFeeds()
    {
        menuitems.getChildren().clear();
    }
}
