/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RSS;

import java.io.IOException;
import java.util.LinkedList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import scrape.Crawler;

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
    
    public RSS(String url) throws IOException
    {
        this.url = url;
        base = parseRSS(url);
        page = Jsoup.parse(base, "", Parser.xmlParser());
        setTitle();
        System.out.println("Fetched " + title);
        Elements stories = getStories();
        //System.out.println(base); //view xml
        //System.out.println(stories.size()); //num stories
        Story current;
        feed = new LinkedList<>();
        System.out.println("Processing " + title);
        for(Element story : stories)
        {
            current = new Story();
            current.title = getStoryTitle(story);
            current.content = getDescription(story);
            current.url = getLink(story);
            current.date = getDate(story);
            feed.add(current);
            
            System.out.println(current);
            System.out.println("------------------------------------------------");
        }
        System.out.println("Completed fetch: " + title);
        //System.out.println(title);
    }
    
    private String parseRSS(String url) throws IOException
    {
        return Parser.unescapeEntities( getPage(url).toString(), false);
    }
    private void setTitle()
    {
        title = page.select("title").first().text();
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
}
