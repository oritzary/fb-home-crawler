import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.text.html.HTML;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlResetInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class FBLogin
{
   private static String file_name = "page2";
	
        public static void readHtmlPage(FileOutputStream out) {
        	
              try {
            	   FileInputStream fstream = new FileInputStream(fileName);
                   DataInputStream in = new DataInputStream(fstream);
                   BufferedReader br = new BufferedReader(new InputStreamReader(in));
                   String strLine;                  
                   while ((strLine = br.readLine()) != null) {
                          if(strLine.contains("https://")){
                        	  out.write(strLine.length());                        	  
                          }                              
                   }                  
                   in.close();
                   out.close();
              } 
              catch (Exception e) {
                   System.err.println("Error: " + e.getMessage());
              }
                  
        }
	
    public static void main(String[] args) throws Exception {
                
            WebClient wc = new WebClient();
	        HtmlPage page = (HtmlPage) wc.getPage("http://www.facebook.com/hongkedavid");	        
			List<HtmlForm> form_list = page.getForms();
	        System.out.println(form_list.size() + " forms in this page"); 
	        	        	 
	        File page2Fb = new File(fileName);
	        FileOutputStream ostream = new FileOutputStream(page2Fb);	        	       
	        readHtmlPage(ostream);
                        	            
    }
}
