package fb.crawler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class FBLogin
{
	private static String file_name = "page2.html";
	private static String outFileName = "output";			
	public static void main(String[] args) throws Exception {	 		
	    getHtmlPage();
	    readHtmlPage();
	}
	
	public static void readHtmlPage() {
        
	      try {
	           FileInputStream fstream = new FileInputStream(file_name);
	           FileWriter outFile = new FileWriter(outFileName);
	           DataInputStream in = new DataInputStream(fstream);
	           BufferedReader br = new BufferedReader(new InputStreamReader(in));
	           String strLine;
	      
	           int indexBegin;
	           int indexEnd;	   
	           String temp;
	           while ((strLine = br.readLine()) != null) {
	        	   indexBegin = 0;	        	   
	        	   if(strLine.contains("https:")){	        		   
	        		   while(indexBegin >= 0){
	        			   indexBegin = strLine.indexOf("https://",indexBegin+1);
	        			   if(indexBegin >= 0){
	        				   indexEnd = strLine.indexOf("\"",indexBegin+1);
	        				   temp = strLine.substring(indexBegin, indexEnd);
	        				   getImage(temp);	        				   
	        				   outFile.append(temp);
	        				   outFile.append("\n");
	        			   }
		        	   }
	        	   }      	   
	           }	          
	           in.close();
	           outFile.close();
	      } 
	      catch (Exception e) {
	           System.err.println("Error: " + e.getMessage());
	      }
	          
	}
	
	public static void getHtmlPage() throws Exception {
	        
		WebClient wc = new WebClient();
	    HtmlPage page = (HtmlPage) wc.getPage("https://www.facebook.com");
		List<HtmlForm> form_list = page.getForms();
		System.out.println(form_list.size() + " forms in this page"); 
		
		HtmlForm form = form_list.get(0);
		HtmlTextInput email = (HtmlTextInput) form.getInputByName("email");
		email.setText("");
		HtmlPasswordInput passwd = (HtmlPasswordInput) form.getInputByName("pass");
		passwd.setText("");
		
		HtmlSubmitInput button = (HtmlSubmitInput) form.getInputByValue("Log In");
		HtmlPage page2 = (HtmlPage) button.click();
		
		form_list = page2.getForms();
		System.out.println(form_list.size() + " forms in this page");

		File page2_fd = new File(file_name);
		page2.save(page2_fd);
		System.out.println(page2_fd.length() + " bytes");
		    
	}

    public static void getImage(String url) {

        String imgUrl = "";        
        if (url.indexOf("fbcdn", 0) >= 0) {
                imgUrl = url;
                imgUrl = NewUrl.replaceUrl(imgUrl, url);
        }
                
        imgUrl = NewUrl.checkUrlPostfix(imgUrl, url);
        
        if (imgUrl.length() > 0) {
        	System.out.println(imgUrl);
        	try {
                InputStream in = new URL(imgUrl).openStream();
                OutputStream out = new BufferedOutputStream(new FileOutputStream(imgUrl.substring(imgUrl.lastIndexOf("/") + 1)));
                int b; 
                while ((b = in.read()) != -1) 
                	out.write(b);
                
                out.close();
                in.close();
             } 
        	catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }  
    }	
   
}