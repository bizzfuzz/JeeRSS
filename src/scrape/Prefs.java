/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import java.util.LinkedList;

/**
 *
 * @author ian
 */
public class Prefs
{
    public boolean linear;
    public String savedir,workdir;
    public LinkedList<String> sources;
    
    public Prefs()
    {
        workdir= "/home/ian/dev/java/dl/";
        savedir = "";
        sources = new LinkedList<>();
    }
}
