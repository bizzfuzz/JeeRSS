/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rss;

import java.io.IOException;
import org.jsoup.nodes.Document;
import scrape.ArticleExtractor;
import scrape.Crawler;
import scrape.Util;
import ui.StoryButton;

/**
 *
 * @author ian
 */
public class Story extends Crawler
{
    public String title;
    public String url;
    public String content;
    public String date;
    public String article;
    public Document page;
    public StoryButton button;
    public boolean saved;
    
    public String saveString()
    {
        String ret = "<story>\n";
        ret += "\t<title>" + title + "</title>\n"; 
        ret += "\t<url>" + url + "</url>\n";
        ret += "\t<content>" + content + "\n\t</content>\n";
        ret += "\t<date>" + date + "</date>\n";
        ret += "\t<article>" + title + "</article>\n";
        ret += "</story>\n";
        return ret;
    }
    
    public String getArticle() throws IOException, InterruptedException
    {
        if(!Util.emptyString(article))
        {
            System.out.println("article size: " + article.length());
            return article;
        }
        System.out.println("Article: " + url);
        if(page == null)
        {
            page = getPage(url);
            ArticleExtractor ae = new ArticleExtractor(page);
            ae.processPage();
            ae.thread.join();
            article = ae.content;
            return article;
        }
        return "";
    }
    @Override
    public String toString()
    {
        String ret = "";
        ret += "Title: " + title + "\n";
        ret += "Content: " + content + "\n";
        ret += "Date: " + date + "\n";
        ret += "Link: " + url + "\n";
        
        return ret;
    }
}
