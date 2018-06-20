/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ian
 */
public class Filer
{
    public String content(String path) throws IOException
    {
        File file = new File(path);
        if(file.exists())
            return new String(Files.readAllBytes(file.toPath()));
        else
            return "";
    }
    public String content(File file) throws IOException
    {
        if(file.exists())
            return new String(Files.readAllBytes(file.toPath()));
        else
            return "";
    }
    
    //write string to file
    public void write(String content, File file)
    {
        if(content.isEmpty())
            return;
        try (PrintWriter out = new PrintWriter(file))
        {
            out.print(content);
            out.close();
        } 
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(Filer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void append(String text, String path) throws IOException
    {
        File file = new File(path.substring(0, path.lastIndexOf("/")));
        if(!file.exists())
            file.mkdirs();
        
        file = new File(path);
        String content = content(file);
        content = content.concat(text+"\n");
        write(content, file);
    }
    public void truncate(String text, String path) throws IOException
    {
        File file = new File(path.substring(0, path.lastIndexOf("/")));
        if(!file.exists())
            file.mkdirs();
        
        file = new File(path);
        write(text, file);
    }
}
