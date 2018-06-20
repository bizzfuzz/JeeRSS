/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rss;

import java.io.IOException;
import java.util.LinkedList;
import javafx.scene.image.Image;
import jeerss.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import scrape.Crawler;
import scrape.Util;

/**
 *
 * @author ian
 */
public class RSS extends Crawler
{
    public String base;
    public String title;
    public String url;
    Document page;
    public LinkedList<Story> feed;
    public boolean savedstory = false;
    
    public RSS()
    {
        feed = new LinkedList<>();
    }
    
    public RSS(String url) throws IOException
    {
        this.url = url;
        base = parseRSS(url);
        page = Jsoup.parse(base, "", Parser.xmlParser());
        setTitle();
        Session.shared.showStatus("Fetching " + title);
        System.out.println("> Fetched " + title);
        Elements stories = getStories();
        //System.out.println(base); //view xml
        //System.out.println(stories.size()); //num stories
        Story current;
        feed = new LinkedList<>();
        System.out.println("> Processing " + title);
        for(Element story : stories)
        {
            current = new Story();
            current.title = getStoryTitle(story);
            current.content = getDescription(story);
            current.url = getLink(story);
            current.date = getDate(story);
            //current.image = getImage(story);
            feed.add(current);
            
            //System.out.println(current);
            //System.out.println("------------------------------------------------");
        }
        Session.shared.showStatus("Completed fetch: " + title);
        System.out.println("> Completed fetch: " + title);
        //System.out.println(title);
    }
    
    private String parseRSS(String url) throws IOException//get xml from page
    {
        return Parser.unescapeEntities( getPage(url).toString(), false);
    }
    private void setTitle()
    {
        title = page.select("title").first().text();
    }
    public void setTitle(String text)
    {
        title = text;
    }
    public void addStory(Story story)
    {
        feed.add(story);
    }
    private Elements getStories()
    {
        return page.select("item");
    }
    private String getStoryTitle(Element story)
    {
        return story.select("title").first().text();
    }
    private String getDescription(Element story)
    {
        return story.select("description").first().text();
    }
    private String getLink(Element story)
    {
        return story.select("link").first().text();
    }
    private String getDate(Element story)
    {
        Elements fields = story.getAllElements();
        for(Element field : fields)
        {
            if(field.nodeName().toLowerCase().contains("date"))
                return field.text();
        }
        return "";
    }
    private Image getImage(Element story)
    {
        Element imgTag = story.select("img").first();
        String imageUrl = "";
        if(imgTag == null)
        {
            for(Element el : story.getAllElements())
            {
                if(el.nodeName().toLowerCase().contains("media:content"))
                {
                    imageUrl = el.attr("url");
                    if(!Util.emptyString(imageUrl))
                        break;
                }
                if(Util.emptyString(imageUrl) && el.nodeName().toLowerCase().contains("media:thumbnail"))
                {
                    imageUrl = el.attr("url");
                    if(!Util.emptyString(imageUrl))
                        break;
                }
            }
        }
        else
        {
            imageUrl = imgTag.attr("src");
        }
        if(Util.emptyString(imageUrl))
            return null;
        //System.out.println("image: " + imageUrl);
        Image ret = new Image(imageUrl);
        if(ret.isError())
            System.out.println("img error: " + ret.exceptionProperty());
        return ret;
    }
    public Story getStory(int id)
    {
        return feed.get(id);
    }
}
