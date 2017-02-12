
import org.jsoup.Jsoup;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shriram
 */
public class FormFilter {
        String filters[]={"login","signup","register","feedback","password","email","subscribe"};
        boolean isRelevant(String form)
        {
            String formName=Jsoup.parse(form).getElementsByTag("form").attr("name");
            String formId=Jsoup.parse(form).getElementsByTag("form").attr("id");
            for(String filter : filters)
            {
               if(formName.contains(filter)||formId.contains(filter))
                   return false;
            }         
            return true;
        }
}
