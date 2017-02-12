
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
import java.io.IOException;
import java.util.List;
import junit.framework.Assert;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author acer
 */
public class PageRedirect {
    public static void main(String args[]) throws IOException
    {
        final Logger log = Logger.getLogger(
        Thread.currentThread().getStackTrace()[0].getClassName() );
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        try  {
        final HtmlPage page = webClient.getPage("http://www.cortical.io/demos/similarity-explorer/");
      // Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        HtmlInput word1=page.getHtmlElementById("expression1");
        HtmlInput word2=page.getHtmlElementById("expression2");
        word1.setValueAttribute("house");
        word2.setValueAttribute("home");
        HtmlButton submit = page.getHtmlElementById("submit-button");
        webClient.waitForBackgroundJavaScript(30000);
        HtmlPage result = submit.click();
        final String pageAsXml = page.asXml();
        
        //Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

        final String pageAsText = page.asText();
        String score=new String();
        PageRedirect pg=new PageRedirect();
        Thread.sleep(7500);
        
    String text=new String();
    final List<DomElement> spans = page.getElementsByTagName("span");
    for (DomElement element : spans) {
        if (element.getAttribute("class").equals("percent")) {
            text= element.getTextContent();
        }
    }
    System.out.println(text+" % ");
    }
    catch(Exception e)
    {
        System.out.println("Exception is:"+e);
    }
    }
    
}
