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
public class ExtractorDispatch implements Runnable
{
    ArticleExtractor extractor;
    
    public ExtractorDispatch(ArticleExtractor ex)
    {
        extractor = ex;
    }
    @Override
    public void run()
    {
        extractor.extract();
    }
    
}
