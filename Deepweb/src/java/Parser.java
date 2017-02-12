
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator; 
import java.util.List;
import java.util.Map;
import net.didion.jwnl.JWNL;
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
public class Parser {
    
    private static String getUrlSource(String url) throws IOException {
            URL yahoo = new URL(url);
            URLConnection yc = yahoo.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream(), "UTF-8"));
            String inputLine;
            String a = "";
            while ((inputLine = in.readLine()) != null)
                a=a+(inputLine);
            
            in.close();

            return a;
        }
    public static void main(String args[]) throws IOException
    {
        
        //JWNL jwnl=JWNL.initialize();
        //Dictionary.getInstance();
        HashMap<String,String> forms=new HashMap<String,String>();
        HashMap<String,ArrayList<Integer>> formVector=new HashMap<String,ArrayList<Integer>>();
        HashMap<String,ArrayList<String>> formAttributes=new HashMap<String,ArrayList<String>>();
        try
        {
    
                String urls[]={
                                
                              
                        //    BLOODBANK 
                                "http://www.friends2support.org" ,
                                "http://www.bloodhelpers.com",
                                "http://www.indianblooddonors.in/search.aspx",
                                "http://www.savelifeindia.org",
                                "http://www.bloodbankindia.net"   
                                
                                /*"http://www.amazon.in/b?node=1317801031",
                                "http://onlinebooks.library.upenn.edu/search.html",
                                "http://www.capstonepub.com/library/service/advanced-search/",
                                "https://www.bookdepository.com/search/advanced",
                                "http://www.biblio.com/usedbooksearch.bib"*/
                                
                              
                };
             int ind=0;
            while (ind<urls.length) 
            {
                String url=urls[ind];
                String src=getUrlSource(url).toLowerCase();
           //     System.out.println(src);
                Tag t=new Tag();
                System.out.println(url);
                String form_content = t.getTagValues(src,"form");
                for(int i=0;i<50;i++){
                    System.out.println("");
                }
                //System.out.println("Form content:\n");
//                form_content=maxChildren(form_content);
        //        System.out.println(form_content);
                int it=0,curr_form_index=0,name_index,form_end_index=0,length=form_content.length();
                String temp="",name="";
                FormFilter filter=new FormFilter();
                VectorGenerator vector=new VectorGenerator();
                while(!form_content.equals(""))
                {
                    form_end_index=(form_content.indexOf("</form>"))+6;
                    temp=form_content.substring(curr_form_index, form_end_index);
                    temp=temp+'>';
                    Document doc=Jsoup.parse(temp);
                    Elements elements=doc.getElementsByTag("form");
                    name=elements.attr("name");
                    String id=elements.attr("id");
                    if(filter.isRelevant(temp))
                    {
                        if(!name.equals("")){
                        forms.put(url+"]"+name,temp);
                        if(!formVector.containsKey(url+"]"+name))
                        formVector.put(url+"]"+name,vector.getVector(temp));
                        }
                        else
                        {
                            forms.put(url+"]"+id,temp);
                            if(!formVector.containsKey(url+"]"+id))
                            formVector.put(url+"]"+id,vector.getVector(temp));
                        }
                    }
                    form_content=form_content.substring(form_end_index+1);
                    
                }
                
                ind++;
            }
        }
        catch(Exception ex) 
        {
            System.out.println(ex.toString());
        }
        
            //    System.out.println("Filtered Forms");
            //    System.out.println(forms);
                for(int i=1;i<=50;i++)
                    System.out.println();
                System.out.println("<  =============================================================     >");
             
                System.out.println("Vector Generation : ");
                    System.out.println();
                KNNClassifier classifier=new KNNClassifier();
                ArrayList<String> classifiedForms=classifier.classify(formVector, 5);
                System.out.println(classifiedForms);
                HashMap<String,String> deepWebQueryForms=new HashMap<String,String>();
                Iterator it=classifiedForms.listIterator();
                
                for(int i=1;i<=50;i++)
                    System.out.println();
                while(it.hasNext())
                {
                    String fname=it.next().toString();
                //    System.out.println(fname);
                    System.out.println();
                    deepWebQueryForms.put(fname,forms.get(fname));
                  //  System.out.println(forms.get(fname));
                }
               Iterator it1=classifiedForms.listIterator();
               System.out.println();
               System.out.println();
               System.out.println();
               System.out.println("Attributes");
               System.out.println();
               System.out.println();
               Attributes attribs=new Attributes();
               while(it1.hasNext())
                {
                    String fname=it1.next().toString();
                    //System.out.println(fname + "   :   " + attribs.getBookAttribs(forms.get(fname)));
                    System.out.println(fname + "   :   " + attribs.getBloodBankAttribs(forms.get(fname)));
                }
               PageRedirect p=new PageRedirect();
               System.out.println(p.compareAttributes("home", "house"));
               
    }
            
            
            
}        


