import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
	
public static void getImage(String url) {

		  //System.out.println("url: " + url);
		  String img_url = "";
          int i;
		  if (url.indexOf("fbcdn", 0) >= 0) {
			  img_url = url;
			  i = url.indexOf("src=", 0);
			  if (i >= 0) {
				  img_url = url;img_url = url.substring(i + "src=".length());
			      i = img_url.indexOf("&amp", 0);	  
			      img_url = img_url.substring(0, i);
			      i = img_url.indexOf("%3A");
			      while (i >= 0) {
			    	  img_url = img_url.substring(0, i) + ":" + img_url.substring(i + "%3A".length());
			    	  i = img_url.indexOf("%3A");
			      }
			      i = img_url.indexOf("%2F");
			      while (i >= 0) {
			    	  img_url = img_url.substring(0, i) + "/" + img_url.substring(i + "%2F".length());
			    	  i = img_url.indexOf("%2F");
			      }
		      }
			  
			  if (img_url.endsWith(".png") == false && 
	        	  img_url.endsWith(".gif") == false &&
	        	  img_url.endsWith(".jpg") == false) {
				  if (img_url.indexOf(".jpg") >= 0)
					  img_url = img_url.substring(0, img_url.indexOf(".jpg") + ".jpg".length());
				  else if (img_url.indexOf(".gif") >= 0)
					  img_url = img_url.substring(0, img_url.indexOf(".gif") + ".gif".length());
				  else if (img_url.indexOf(".png") >= 0)
					  img_url = img_url.substring(0, img_url.indexOf(".png") + ".png".length());
			  }
		  }
		  else if (url.endsWith(".png") == true || 
        	       url.endsWith(".gif") == true ||
        	       url.endsWith(".jpg") == true) {
			  img_url = url;
          }
        		  
          if (img_url.length() > 0) {
        	  System.out.println(img_url);
        	  try {
				  BufferedReader r = new BufferedReader(new InputStreamReader(new URL(img_url).openStream()));
			  } 
        	  catch (Exception e) {
   		          System.err.println("Error: " + e.getMessage());
   		      }
          }  
	}
	
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
		        		   getImage(strLine.substring(i, j));
		        		   //System.out.println(k + " " + strLine.substring(i, j));
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
        email.setText("");
        HtmlPasswordInput passwd = (HtmlPasswordInput) form.getInputByName("pass");
        passwd.setText("");
        
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
	  	
		//getHtmlPage();
		readHtmlPage();
        
    }
}
