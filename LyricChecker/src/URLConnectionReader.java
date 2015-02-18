// credit: Joseph Weissman http://stackoverflow.com/questions/4328711/read-url-to-string-in-few-lines-of-java-code
import java.net.*;
import java.io.*;

public class URLConnectionReader 
{
  public static String getText(String url) throws Exception
  {
    URL website = new URL(url);
    URLConnection connection = website.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
  
    StringBuilder response = new StringBuilder();
    String inputLine;
    
    while ((inputLine = in.readLine()) != null) 
      response.append(inputLine);
    
    in.close();
    
    return response.toString();
  }
  
  public static void main(String[] args) throws Exception 
  {
    String content = URLConnectionReader.getText(args[0]);
    System.out.println(content);
  }
}