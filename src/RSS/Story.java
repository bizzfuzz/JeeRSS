/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RSS;

/**
 *
 * @author ian
 */
public class Story
{
    public String title;
    public String url;
    public String content;
    public String date;
    
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
