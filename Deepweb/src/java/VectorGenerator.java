
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shriram
 */
public class VectorGenerator {
        ArrayList<Integer> vectorArray=new ArrayList<Integer>();
        ArrayList<Integer> getVector(String form)
        {
            
                Document doc=Jsoup.parse(form);
                
                vectorArray.add(doc.getElementsByTag("input").size());
                vectorArray.add(doc.getElementsByTag("submit").size()+doc.getElementsByTag("img").size());
                vectorArray.add(doc.getElementsByTag("select").size());
                vectorArray.add(doc.getElementsByTag("textarea").size());
                
                return vectorArray;
        }
}
