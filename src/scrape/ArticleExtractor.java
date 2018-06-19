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
        boolean contentstarted = false;
        //System.out.println(">\textracting");
        Element body = page.select("body").first();
        int threshold = 130;
        content = "<html><head><link type=\"text/css\" rel=\"stylesheet\" href=\"article.css\"></head><body>\n";
        String[] skip = {
            "facebook.", "google.", "twitter.", "patreon.", "share.", "google-plus.", "discord.", "youtube.",
            "steam.", "telegram.", "search.", "rss."
        };
        for(Element tag : body.getAllElements())
        {
            if(tag.nodeName().equals("p"))
            {
                String tagtext = tag.text();
                if(tagtext.length() > threshold)
                {
                    if(!contentstarted)
                        contentstarted = true;
                    content += "<p>" + tagtext + "</p>";
                }
                else
                {
                    //path += "/" + tag.nodeName();
                    /*
                    System.out.println("-------------passed---------------");
                    System.out.println(">Processing tag: " + path);
                    System.out.println(tagtext);
                    System.out.println(tagtext.length());*/
                }
            }
            else if(contentstarted && tag.nodeName().equals("img"))//TODO: distinguish article pics from user coments and social media site pics
            {
                String tagrep = tag.toString();
                //System.out.println(tagrep);
                boolean toskip = false;
                for(String ban : skip)
                    if(tag.attr("src").contains(ban))
                        toskip = true;
                if(!toskip)
                {
                    //System.out.println(tagrep);
                    content += "<br>" +  tagrep + "<br>";
                }
            }
            else if(contentstarted && tag.nodeName().equals("ul"))
            {
                content += "<br>" + tag.toString() + "<br>";
            }
        }
        content += "</body></html>";
        //System.out.println("///////////////////////////////////////");
    }
}
