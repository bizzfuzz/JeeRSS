/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

/**
 *
 * @author ian
 */
public class Prefs
{
    public int minLength;
    public int minRating;
    public int nVids;
    public boolean linear;
    public String savedir,workdir;
    public String[] sources;
    
    public Prefs()
    {
        workdir= "/home/ian/dev/java/dl/";
        savedir ="";
        sources = new String[]{
            "pornhub", "tube8","extremetube", "youporn", "spankwire",
            "redtube", "keezmovies", "mofosex", "xtube"
        };
        minRating=0;
        minLength=0;
        linear=true;
        nVids=1;
    }
}
