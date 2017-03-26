
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    static HashMap<String, ArrayList<String>> hm;
    static String strappend;
    private static String getUrlSource(String url) throws IOException {
        URL yahoo = new URL(url);
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream(), "UTF-8"));
        String inputLine;
        String a = "";
        while ((inputLine = in.readLine()) != null) {
            a = a + (inputLine);
        }

        in.close();

        return a;
    }

    public static void main(String args[]) throws IOException {
        hm = new HashMap<String, ArrayList<String>>();
        strappend="";
        //JWNL jwnl=JWNL.initialize();
        //Dictionary.getInstance();
        int urllength = 0;
        HashMap<String, String> forms = new HashMap<String, String>();
        HashMap<String, ArrayList<Integer>> formVector = new HashMap<String, ArrayList<Integer>>();
        HashMap<String, ArrayList<String>> formAttributes = new HashMap<String, ArrayList<String>>();
        
        try {

            String urls[] = {
                //    BLOODBANK 
           /*     "http://www.friends2support.org" ,
                   "http://www.bloodhelpers.com"
                //"http://www.indianblooddonors.in/search.aspx",
                //"http://www.savelifeindia.org",
                //"http://www.bloodbankindia.net"   */

                //    "http://www.amazon.in/b?node=1317801031",
                "http://onlinebooks.library.upenn.edu/search.html",
                // "http://www.capstonepub.com/library/service/advanced-search/",
                "https://www.bookdepository.com/search/advanced"
            //"http://www.biblio.com/usedbooksearch.bib"

            };
            int ind = 0;
            urllength = urls.length;

            while (ind < urls.length) {
                String url = urls[ind];
                String src = getUrlSource(url).toLowerCase();
                //     System.out.println(src);
                Tag t = new Tag();
                System.out.println(url);
                String form_content = t.getTagValues(src, "form");
                for (int i = 0; i < 50; i++) {
                    System.out.println("");
                }
                //System.out.println("Form content:\n");
//                form_content=maxChildren(form_content);
                //        System.out.println(form_content);
                int it = 0, curr_form_index = 0, name_index, form_end_index = 0, length = form_content.length();
                String temp = "", name = "";
                FormFilter filter = new FormFilter();
                VectorGenerator vector = new VectorGenerator();
                while (!form_content.equals("")) {
                    form_end_index = (form_content.indexOf("</form>")) + 6;
                    temp = form_content.substring(curr_form_index, form_end_index);
                    temp = temp + '>';
                    Document doc = Jsoup.parse(temp);
                    Elements elements = doc.getElementsByTag("form");
                    name = elements.attr("name");
                    String id = elements.attr("id");
                    if (filter.isRelevant(temp)) {
                        if (!name.equals("")) {
                            forms.put(url + "]" + name, temp);
                            if (!formVector.containsKey(url + "]" + name)) {
                                formVector.put(url + "]" + name, vector.getVector(temp));
                            }
                        } else {
                            forms.put(url + "]" + id, temp);
                            if (!formVector.containsKey(url + "]" + id)) {
                                formVector.put(url + "]" + id, vector.getVector(temp));
                            }
                        }
                    }
                    form_content = form_content.substring(form_end_index + 1);

                }

                ind++;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        //    System.out.println("Filtered Forms");
        //    System.out.println(forms);
        for (int i = 1; i <= 50; i++) {
            System.out.println();
        }
        System.out.println("<  =============================================================     >");

        System.out.println("Vector Generation : ");
        System.out.println();
        KNNClassifier classifier = new KNNClassifier();
        ArrayList<String> classifiedForms = classifier.classify(formVector, 2);
        System.out.println(classifiedForms);
        HashMap<String, String> deepWebQueryForms = new HashMap<String, String>();
        Iterator it = classifiedForms.listIterator();

        for (int i = 1; i <= 50; i++) {
            System.out.println();
        }
        while (it.hasNext()) {
            String fname = it.next().toString();
            //    System.out.println(fname);
            System.out.println();
            deepWebQueryForms.put(fname, forms.get(fname));
            //  System.out.println(forms.get(fname));
        }
        Iterator it1 = classifiedForms.listIterator();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Attributes");
        System.out.println();
        System.out.println();
        Attributes attribs = new Attributes();
        
        //HashMap<String,Integer> hm_len=new HashMap<String,Integer>();
        ArrayList<String> Books = new ArrayList();
        //        int len=urllength;
        //      int arrangement[]=new int[len],findex=0;
        while (it1.hasNext()) {
            String fname = it1.next().toString();
            ArrayList<String> attributes = attribs.getBookAttribs(forms.get(fname));
            hm.put(fname, attributes);
            strappend=strappend+fname;
            for(String atrbs:attributes)
                strappend = strappend + atrbs;
            System.out.println(fname + "   :   " + attributes + " " + attributes.size());
            //  System.out.println(fname + "   :   " + attribs.getBloodBankAttribs(forms.get(fname)));

            //     arrangement[findex++]=attributes.size();
            // hm_len.put(fname,attributes.size());
        }

        ArrayList<String> al = (new Parser()).integrate(hm);

        HtmlCreate h = new HtmlCreate();
        h.HtmlCrea(al);
        /*hm_len=(HashMap<String,Integer>)((new KNNClassifier()).sortByValue(hm_len));
                System.out.println(hm_len);
                 Iterator mapIt =   hm_len.entrySet().iterator();
                 HashMap<String,ArrayList<String>> hm_attribs=new HashMap<String,ArrayList<String>>(); 
              while(mapIt.hasNext())
              {
                   String formname=mapIt.next().toString();
                   hm_attribs.put(formname, hm.get(formname));
              }
              System.out.println(hm_attribs);*/

        PageRedirect p = new PageRedirect();

    }

    ArrayList<String> integrate(HashMap<String, ArrayList<String>> hm) throws IOException {
        final Logger log = Logger.getLogger(
                Thread.currentThread().getStackTrace()[0].getClassName());
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);

        final HtmlPage page = webClient.getPage("http://www.cortical.io/demos/similarity-explorer/");
        // Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());

        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        HtmlInput word1 = page.getHtmlElementById("expression1");
        HtmlInput word2 = page.getHtmlElementById("expression2");
        ArrayList<String> attributes = new ArrayList<String>();
        Set<String> matched = new HashSet<String>();

        int max_match = 0, ind = 0;
        Map.Entry pair1, pair2 = null;
        Iterator attrIt = hm.entrySet().iterator();
        String matchedword = "";
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        while (attrIt.hasNext()) {

            if (ind == 0) {
                pair1 = (Map.Entry) attrIt.next();
            } else {
                pair1 = pair2;
            }
            if (attrIt.hasNext()) {
                pair2 = (Map.Entry) attrIt.next();
                list1 = (List<String>) pair1.getValue();
                list2 = (List<String>) pair2.getValue();
                if (list1.size() > list2.size()) {
                    List<String> temp = list1;
                    list1 = list2;
                    list2 = temp;
                }
                for (String e1 : list1)//smaller list
                {
                    if (!attributes.contains(e1) && !matched.contains(e1)) {
                        max_match = 0;
                        int match;
                        attributes.add(e1);
                        for (String e2 : list2)//larger list
                        {
                            if (e1.equals(e2) || e1.contains(e2) || e2.contains(e1))//exact or partial match
                            {
                                max_match=100;
                                matchedword = e2;
                                System.out.println(e1 + "  " + e2 + " = > " + max_match + " %");
                                break;
                            }
                            PageRedirect p = new PageRedirect();
                            match = p.estimate(e1, e2, page, word1, word2, webClient);//% matched
                            System.out.println(e1 + "  " + e2 + " = > " + match +" %");
                            if (match > max_match) {
                                max_match = match;
                                matchedword = e2;
                                if (max_match == 100) {
                                    break;
                                }

                            }
                        }
                    }
                    matched.add(matchedword);
                    
                }

                System.out.println(list1 + "    " + list2);
                for (String e2 : list2) {
                    if (!attributes.contains(e2)) {
                        attributes.add(e2);
                    }
                }

            } else {

            }

            attrIt.remove(); // avoids a ConcurrentModificationException
            ind++;
        }
        return attributes;
    }
    public HashMap<String,ArrayList<String>> getForms()
    {
        return hm;
    }
    public static String getFormsAttribs()
    {
        return strappend;
    }
            
}
