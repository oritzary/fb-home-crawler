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
	private static String file_name = "page2.html";
	
	public static void readHtmlPage() {
		
		  try {
		       FileInputStream fstream = new FileInputStream(file_name);
		       DataInputStream in = new DataInputStream(fstream);
		       BufferedReader br = new BufferedReader(new InputStreamReader(in));
		       String strLine;
		  
		       int k = 1, j, i;
		       while ((strLine = br.readLine()) != null) {
		    	   i = 0;
		           while (i >= 0)
		           {
		        	   i = strLine.indexOf("https://", i + 1);
		        	   if (i >= 0)
		        	   {
		        		   j = strLine.indexOf("\"", i + 1);
		        		   System.out.println(k + " " + strLine.substring(i, j));
		        	   }
		           }
		           k++;
		       }
		  
		       in.close();
		  } 
		  catch (Exception e) {
		       System.err.println("Error: " + e.getMessage());
		  }
		  
	}
	
	public static void getHtmlPage() throws Exception {
		
		WebClient wc = new WebClient();
        HtmlPage page = (HtmlPage) wc.getPage("http://www.facebook.com/hongkedavid");
        List<HtmlForm> form_list = page.getForms();
        System.out.println(form_list.size() + " forms in this page"); 
        
        HtmlForm form = form_list.get(0);//page.getFormByName("login_form");
        HtmlTextInput email = (HtmlTextInput) form.getInputByName("email");
        email.setText("hongkedavid@gmail.com");
        HtmlPasswordInput passwd = (HtmlPasswordInput) form.getInputByName("pass");
        passwd.setText("88927Hk");
        
        HtmlSubmitInput button = (HtmlSubmitInput) form.getInputByValue("Log In");
        HtmlPage page2 = (HtmlPage) button.click();
        
        form_list = page2.getForms();
        System.out.println(form_list.size() + " forms in this page");
        //DomNodeList<HtmlElement> img_list = page2.getElementsByTagName("img");
        //System.out.println(img_list.size() + " images in this page");
        //for (int i = 0; i < img_list.size(); i++)
        //	System.out.println(img_list.get(i).getId());
        
        File page2_fd = new File(file_name);
        page2.save(page2_fd);
        System.out.println(page2_fd.length() + " bytes");
        //System.out.println(page2_fd.canRead() + " " + page2_fd.canWrite());
	
	}
	
	public static void main(String[] args) throws Exception {
	  	
		getHtmlPage();
		readHtmlPage();
        
    }
}
