/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.LinkedList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 *
 * @author ian
 */
public class FeedButton extends Button
{
    LinkedList<StoryButton> stories;
    boolean expand;
    String title;
    
    public FeedButton(String text)
    {
        this.setText(text);
        title = text;
        stories = new LinkedList<>();
        expand = true;
        
        setOnAction((ActionEvent e) -> 
        {
            Platform.runLater(() ->
            {
                switchState();
            });
        });
    }
    public void switchState()
    {
        if(expand)
        {
            hideStories();
        }
        else
        {
            showStories();
        }
        expand = !expand;
    }
    public void hideStories()
    {
        stories.forEach((story) ->
        {
            story.setVisible(false);
            story.setManaged(false);
        });
    }
    public void showStories()
    {
        stories.forEach((story) ->
        {
            story.setVisible(true);
            story.setManaged(true);
        });
    }
    public void addStory(StoryButton story)
    {
        stories.add(story);
    }
}
