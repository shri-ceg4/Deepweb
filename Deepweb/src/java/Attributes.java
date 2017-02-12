
import java.util.ArrayList;
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
public class Attributes {
        public ArrayList<String> getBloodBankAttribs(String form)
        {
                    Document doc = Jsoup.parse(form);
                    Elements labels=doc.getElementsByTag("label");
                    Elements span=doc.getElementsByTag("span");
                    int ind=0;
                    ArrayList<String> attribs=new ArrayList<String>();
                    boolean flag;
                    if(!labels.isEmpty())
                    {
                        for(Element label:labels)
                        {   
                            flag=true;
                            Elements elems=label.siblingElements();
                            for(Element sibling : elems)        
                            {
                                if(sibling.hasAttr("type"))
                                {
                                    if(!sibling.attr("type").equals("text"))
                                    {
                                        flag=false;
                                        break;
                                    }
                                }
                            }
                         
                            if(flag)
                            {  
                                attribs.add(label.text());
                            }
                        }
                    }
                    /*else if(!span.isEmpty())
                    {
                        for(Element spa:span)
                            attribs.add(spa.text());
                    } */      
                    else
                    {
                        Elements input_elements = doc.getElementsByTag("input");
                        if(!input_elements.isEmpty())
                        {
                            for(Element inp: input_elements)
                            {
                              if(inp.hasAttr("type"))
                              {    
                                    if(inp.attr("type").equals("text"))
                                    {
                                        if(inp.hasAttr("name"))
                                        {
                                            attribs.add(inp.attr("name"));
                                        }
                                        else
                                        {
                                            attribs.add(inp.attr("id"));
                                        }
                                    }
                              }
                                else
                                    attribs.add(inp.attr("name"));
                        }
                       }
                      
                      
                           Elements select_elements = doc.getElementsByTag("select");
                           if(!select_elements.isEmpty())
                           {
                                for(Element inp: select_elements)
                            {
                              if(inp.hasAttr("type"))
                              {    
                                    if(inp.attr("type") .equals("text"))
                                    {
                                        if(inp.hasAttr("name"))
                                        {
                                            attribs.add(inp.attr("name"));
                                        }
                                        else
                                        {
                                            attribs.add(inp.attr("id"));
                                        }
                                    }
                                 }
                                else
                                    attribs.add(inp.attr("name"));
                        }
                           }
                      
                    }
                    return attribs;
        }
                public ArrayList<String> getBookAttribs(String form){
        Document doc = Jsoup.parse(form);
                    Elements labels=doc.getElementsByTag("label");
                    Elements span=doc.getElementsByTag("span");
                    int ind=0;
                    ArrayList<String> attribs=new ArrayList<String>();
                    boolean flag;
                    if(!labels.isEmpty())
                    {
                        for(Element label:labels)
                        {   
                            flag=true;
                            Elements elems=label.siblingElements();
                            for(Element sibling : elems)        
                            {
                                if(sibling.hasAttr("type"))
                                {
                                    if((sibling.attr("type").equals("checkbox"))  || (sibling.attr("type").equals("radio") ))
                                    {
                                        flag=false;
                                        break;
                                    }
                                }
                            }
                         
                            if(flag)
                            {  
                                attribs.add(label.text());
                            }
                        }
                    }
                    else if(!span.isEmpty())
                    {
                        for(Element spa:span)
                            attribs.add(spa.text());
                    } 
                    else
                    {
                        Elements input_elements = doc.getElementsByTag("input");
                        if(!input_elements.isEmpty())
                        {
                            for(Element inp: input_elements)
                            {
                              if(inp.hasAttr("type"))
                              {    
                                    if(inp.attr("type").equals("text"))
                                    {
                                        if(inp.hasAttr("name"))
                                        {
                                            attribs.add(inp.attr("name"));
                                        }
                                        else
                                        {
                                            attribs.add(inp.attr("id"));
                                        }
                                    }
                              }
                                else
                                    attribs.add(inp.attr("name"));
                        }
                       }
                      
                      
                           Elements select_elements = doc.getElementsByTag("select");
                           if(!select_elements.isEmpty())
                           {
                                for(Element inp: select_elements)
                            {
                              if(inp.hasAttr("type"))
                              {    
                                    if(inp.attr("type").equals("text"))
                                    {
                                        if(inp.hasAttr("name"))
                                        {
                                            attribs.add(inp.attr("name"));
                                        }
                                        else
                                        {
                                            attribs.add(inp.attr("id"));
                                        }
                                    }
                                 }
                                else
                                    attribs.add(inp.attr("name"));
                        }
                           }
                      
                    }
                    return attribs;
                }
}

