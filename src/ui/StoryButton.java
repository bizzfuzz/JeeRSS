/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.event.ActionEvent;
import rss.Story;

/**
 *
 * @author ian
 */
public class StoryButton extends javafx.scene.control.Button
{
    public Story story;
    public FXMLController controller;
    
    public StoryButton(Story story, FXMLController controller)
    {
        this.story = story;
        this.controller = controller;
        setText(story.title);
        setOnAction((ActionEvent e) -> 
        {
            controller.showStory(story);
        });
    }
}
