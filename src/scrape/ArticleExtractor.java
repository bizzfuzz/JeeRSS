/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author ian
 */
public class ArticleExtractor
{
    public Document page;
    public Thread thread;
    public String content;
    
    public ArticleExtractor()
    {
        init();
    }
    public ArticleExtractor(Document p)
    {
        page = p;
        init();
    }
    private void init()
    {
        //thread = new Thread(new ExtractorDispatch(this));
    }
    public void setPage(Document p)
    {
        page = p;
    }
    public void processPage(Document p)
    {
        setPage(p);
        processPage();
    }
    public void processPage()
    {
        System.out.println(">\tprocessing page: " + page.location());
        if(page != null)
        {
            thread = new Thread(new ExtractorDispatch(this));
            thread.start();
        }
    }
    public static String extract(String html)
    {
        return "";
    }
    public void extract()
    {
        System.out.println(">\textracting");
        Element body = page.select("body").first();
        int threshold = 130;
        content = "<html><head><link type=\"text/css\" rel=\"stylesheet\" href=\"article.css\"></head><body>\n";
        body.getAllElements().forEach((tag) ->
        {
            if(tag.nodeName().equals("p"))
            {
                String path = body.nodeName();
                String tagtext = tag.text();
                if(tagtext.length() > threshold)
                {
                    path += "/" + tag.nodeName();
                    System.out.println("----------------------------");
                    System.out.println(">Processing tag: " + path);
                    System.out.println(tagtext);
                    System.out.println(tagtext.length());
                    content += "<p>" + tagtext + "</p>";
                }
            }
        });
        content += "</body></html>";
        System.out.println("///////////////////////////////////////");
    }
}
