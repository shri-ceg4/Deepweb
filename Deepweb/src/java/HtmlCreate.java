/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shriram
 */
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HtmlCreate {
    public void HtmlCrea(ArrayList<String> Al) throws IOException {

String filename="C:\\Users\\nshri_000\\Desktop\\Deepweb-master\\Deepweb\\web\\Bookdomain0235.html";
File file = new File(filename);

if(file.exists()){
System.out.println("File already exist");
}else{
FileWriter fileWriter = null;
BufferedWriter bufferedWriter = null;
try {
fileWriter = new FileWriter(file);
bufferedWriter = new BufferedWriter(fileWriter);

String htmlPage = "<html><body style=’background-color:#ccc’><b><h3><center><u>BOOK</u></center></h3></b> ";

bufferedWriter.write(htmlPage);
bufferedWriter.append("<form action=\"index.jsp\">" );
Iterator it=Al.iterator();
while(it.hasNext()){
    String s=it.next().toString();
    bufferedWriter.append(s + "<input type=\"text\" name="+s+"> <br><br> ");  
}
Parser p=new Parser();
String append;
    append = Parser.getFormsAttribs();
/*HashMap<String,ArrayList<String>> hm=p.getForms();
               for (Map.Entry<String, ArrayList<String>> entry : hm.entrySet()) {
                                 String key = entry.getKey();
                                 append = append + key;
               }*/
 
bufferedWriter.append("<div style=\"display:none\"> <input type=\"text\" name=\"forms\" value="+append+"> </div>");
bufferedWriter.append("<input type=\"submit\" value=\"Submit\"></form>");
bufferedWriter.append("</body></html>");

System.out.println("Html page created");

Desktop.getDesktop().browse(file.toURI());
    
bufferedWriter.flush();
fileWriter.flush();

} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}finally{
try {

bufferedWriter.close();
fileWriter.close();
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

}

}

}
    
}
