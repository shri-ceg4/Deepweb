
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
public class Tag {
    
    private static final Pattern TAG_REGEX = Pattern.compile("<form>(.+?)</form>");
    public static List<String> getTagValues(final String str) {
    final List<String> tagValues = new ArrayList<String>();
    final Matcher matcher = TAG_REGEX.matcher(str);
    while (matcher.find()) {
        tagValues.add(matcher.group(1));
    }
    return tagValues;
}
    public static String getTagValues(String input,String tag){
        Document doc = Jsoup.parse(input);
        Elements elements = doc.getElementsByTag(tag);

        Elements group = new Elements();
        //group.add(elements.first());
        FormFilter ff=new FormFilter();
        int maxsize=0,index=0,ind=0;
        for( Element element : elements )
        {
            if(ff.isRelevant(element.toString()))
            {
                int size = element.childNodeSize();
                //System.out.println(size);
                if(size>=maxsize){
                    maxsize=size;
                    ind=index;
                }
            
            }
            index++;
        }
        index=0;
        for(Element elem:elements)
        {
            if(ind==index)
            {
                System.out.println(elem.childNodeSize());
                group.add(elem);
                break;
            }
            index++;
        }

                
         
        String output = group.toString();
        return output;
    }
    
}

