package fb.crawler;

public class NewUrl {
	
	public NewUrl(){	
		
	}
	
	public static String replaceUrl(String imgUrl, String url){
        int index = url.indexOf("src=", 0);
        if (index >= 0) {
            imgUrl = url;
            imgUrl = url.substring(index + "src=".length());
            index = imgUrl.indexOf("&amp", 0);     
            imgUrl = imgUrl.substring(0, index);
            index = imgUrl.indexOf("%3A");
            while (index >= 0) {
                imgUrl = imgUrl.substring(0, index) + ":" + imgUrl.substring(index + "%3A".length());
                index = imgUrl.indexOf("%3A");
            }
            index = imgUrl.indexOf("%2F");
            while (index >= 0) {
                imgUrl = imgUrl.substring(0, index) + "/" + imgUrl.substring(index + "%2F".length());
                index = imgUrl.indexOf("%2F");
            }
        }
		return imgUrl;		
	}
	
	public static String checkUrlPostfix(String imgUrl, String url){
        if (imgUrl.endsWith(".png") == false && 
        imgUrl.endsWith(".gif") == false &&
        imgUrl.endsWith(".jpg") == false) {
                if (imgUrl.indexOf(".jpg") >= 0)
                        imgUrl = imgUrl.substring(0, imgUrl.indexOf(".jpg") + ".jpg".length());
                else if (imgUrl.indexOf(".gif") >= 0)
                        imgUrl = imgUrl.substring(0, imgUrl.indexOf(".gif") + ".gif".length());
                else if (imgUrl.indexOf(".png") >= 0)
                        imgUrl = imgUrl.substring(0, imgUrl.indexOf(".png") + ".png".length());
        }
        
        else if (url.endsWith(".png") == true || url.endsWith(".gif") == true || url.endsWith(".jpg") == true) 
                imgUrl = url;
		return imgUrl;		
	}
	 
}
