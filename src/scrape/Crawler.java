/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author ian
 */
public class Crawler
{
    private String domain;
    
    //decode grabbed string into usable format
    public String decode(String url) throws ScriptException
    {
        ScriptEngineManager se = new ScriptEngineManager();
        ScriptEngine engine = se.getEngineByName("JavaScript");
        return (String) engine.eval("decodeURIComponent('"+url+"')");
    }
    
    private void downImages(Document doc, Prefs set) throws IOException
    {
        Elements images = getImages(doc);
        String link;
        
        for(Element image : images)
        {
            link = image.attr("src").trim();
            link = sanitize(link);
            saveFile(link,set);
        }
    }
    
    public void saveFile(String link, Prefs set) throws MalformedURLException, IOException
    {
        File file = new File(set.savedir+filename(link));
        if(file.exists())
            return;
        URL url = new URL(link);
        InputStream in = url.openStream();
        Files.copy(in, file.toPath());
        System.out.println(file.toPath()+" saved");
    }
    
    private String extension(String file)
    {
        int i = file.lastIndexOf(".");
        return file.substring(i+1);
    }
    
    private String filename(String url)
    {
        int i = url.lastIndexOf("/");
        return url.substring(i+1);
    }
    
    //remove whitespace / slashes not needed
    private String sanitize(String url)
    {
        String ret=url;
        if(!ret.startsWith("http"))
            ret = domain+ret;
        
        if(ret.endsWith("/"))
            ret = ret.substring(0, ret.length()-1);
        
        if(ret.contains("#"))
            ret = ret.substring(0, ret.indexOf("#"));
        return ret;
    }
    
    private Elements getLinks(Document doc)
    {
        return doc.select("a[href]");
    }
    
    public Document getPage(String url) throws IOException
    {
        return Jsoup.connect(sanitize(url))
            .userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0")
            .timeout(10000)
            .get();
    }
    
    public Elements getImages(Document doc) throws IOException
    {
        return doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
    }
    
    public int filesize(String url) throws IOException
    {
        URL link = new URL(url);
        int len = link.openConnection().getContentLength();
        //System.out.println(formatSize(len));
        return len;
    }
    
    public String formatSize(int size)
    {
        String suffix="KB";
        float ret= size/1000;
        if(ret>9999)
        {
            ret=ret/1000;
            suffix="MB";
        }
        return ret+suffix;
    }
    
    public String formatSize(float size)
    {
        String suffix="KB";
        float num= size/1000;
        if(num>9999)
        {
            num=num/1000;
            suffix="MB";
        }
        return String.format("%.3f%s", num,suffix);//ret+suffix;
    }
}
