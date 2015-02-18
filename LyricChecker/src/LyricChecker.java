import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.*;  //for ActionListener, ActionEvent
import javax.swing.*;  //for JFrame, BoxLayout, JLabel, JTextField, JButton
import java.util.LinkedList;
import java.util.Arrays;

public class LyricChecker
{
  String webText;
  Scanner scan;
  private String songLyrics;
  private String artistText;
  private String songText;
  private String badWords;
  private boolean hasBadWords;
  private boolean hasQWords;
  //private boolean urlInvalid;
  
  //input artist and song; get song lyrics; 
  //get list of swear words; return swear words and maybe number of occurences; 
  //deal with apostrophes and stuff;
  public LyricChecker(String artist, String song)  //takes in human input artist and song
  { 
    useAZ(artist, song);
    badWords = findBadWords(songLyrics);
  }
  public static void main(String[] args)
  {
//    LyricChecker l = new LyricChecker("eminem", "cold wind blows");
//    System.out.println(l.getLyrics());
    LyricChecker c = new LyricChecker("eminem", "lose yourself");
    System.out.println(c.getLyrics());
  }
  
  public void useAZ(String artist, String song)
  {
    try
    {
      String webText;
      Scanner scan;
      artist  = processAZ(artist);
      if(artist.substring(0, 3).equals("the"))
        artist = artist.substring(3);
      song = processAZ(song);
      
      webText = URLConnectionReader.getText("http://www.azlyrics.com/lyrics/" + artist +"/" + song + ".html");
      scan = new Scanner(webText);
      
      scan.useDelimiter("artist: \"");
      scan.next();
      scan.useDelimiter("\"");
      scan.next();
      artistText = scan.next();
//      if(artistText.length() > 20)
//        artistText = artistText.substring(0, 17) + "...";
      
      scan.useDelimiter("song: \"");
      scan.next();
      scan.useDelimiter("\"");
      scan.next();
      songText = scan.next();
//      if(songText.length() > 20)
//        songText = songText.substring(0, 17) + "...";
      
      scan.useDelimiter("<!-- start of lyrics -->");
      scan.next();
      scan.useDelimiter("<!-- end of lyrics -->");
      songLyrics = scan.next().substring(23);
    }
    catch(Exception ex)
    {
      return;
    }
  }
  
//  public void useMetro(String artist, String song)
//  {
//    String webText;
//    Scanner scan;
//    artist  = processMetro(artist);
//    song  = processMetro(song);
//    webText = URLConnectionReader.getText("http://www.metrolyrics.com/"+song+"-lyrics-"+artist+".html");
//    scan = new Scanner(webText);
//  }
  
  public String findBadWords(String lyrics)
  {         
    String webText;
    Scanner scan;
    hasBadWords = true;
    hasQWords = true;
    String bad = "Bad words: ";
    String q = "Questionable words: ";
    LinkedList<String> badWords = new LinkedList<String>(Arrays.asList("asshole","bitch","cocksucker","cunt","dick","fuck","nigga","nigger","piss","pussy","shit"));
    LinkedList<String> qWords = new LinkedList<String>(Arrays.asList(" ass ","bastard","damn","hell","sex"));
    for(int i = 0; i < badWords.size(); i++)
    {
      String currBWord = badWords.get(i);
      if(lyrics.indexOf(currBWord) != -1)
      {
        bad = bad + currBWord.substring(0,1) + currBWord.substring(1,currBWord.length()-1).replaceAll(".", "*") + currBWord.substring(currBWord.length()-1) + "; ";
      }
    }
    for(int i = 0; i < qWords.size(); i++)
    {
      String currQWord = qWords.get(i);
      if(lyrics.indexOf(currQWord) != -1)
      {
        q = q + currQWord + "; ";
      }
    }
    if(bad.equals("Bad words: "))
    {
      bad = bad + "none found. ";
      hasBadWords = false;
    }
    if(q.equals("Questionable words: "))
    {
      q = q + "none found. ";
      hasQWords = false;
    }
    bad = bad.substring(0, bad.length()-2) + ". ";
    q = "\n" + q.substring(0, q.length()-2) + ". ";
    return bad + q;
    
//    hasBadWords = true;      //original method
//    hasQWords = true;
//    String bad = "Bad words: ";
//    if(lyrics.indexOf("fuck") != -1)
//      bad = bad + "f**k; ";
//    if(lyrics.indexOf("shit") != -1)
//      bad = bad + "s**t; ";
//    if(lyrics.indexOf("bitch") != -1)
//      bad = bad + "b***h; ";
//    if(lyrics.indexOf("nigga") != -1)
//      bad = bad + "n***a; ";
//    if(lyrics.indexOf("cunt") != -1)
//      bad = bad + "c**t; ";
//    if(lyrics.indexOf("asshole") != -1)
//      bad = bad + "a*****e; ";
//    if(lyrics.indexOf("dick") != -1)
//      bad = bad + "d**k; ";
//    if(lyrics.indexOf("pussy") != -1)
//      bad = bad + "p***y; ";
//    if(bad.equals("Bad words: "))
//    {
//      bad = bad + "none found! ";
//      hasBadWords = false;
//    }
//    bad = bad.substring(0, bad.length()-2) + ". ";
//    
//    bad = bad + "\nQuestionable words: "; 
//    
//    if(lyrics.indexOf("hell") != -1)
//      bad = bad + "hell; ";
//    if(lyrics.indexOf("sex") != -1)
//      bad = bad + "sex; ";
//    if(lyrics.indexOf("damn") != -1)
//      bad = bad + "damn; ";
//    if(lyrics.indexOf(" ass ") != -1)
//      bad = bad + "ass; ";
//    if(bad.substring(bad.length()-20).equals("Questionable words: "))
//    {
//      bad = bad + "none found! ";
//      hasQWords = false;
//    }
//    return bad.substring(0, bad.length()-2) + ".";
  }
  public static String processAZ(String songOrArtist)
  {
    songOrArtist = songOrArtist.replaceAll("\\s","");  
    songOrArtist = songOrArtist.replaceAll("\"","");
    songOrArtist = songOrArtist.replaceAll("\\.","");
    songOrArtist = songOrArtist.replaceAll("\'","");
    songOrArtist = songOrArtist.replaceAll(",","");   
    songOrArtist = songOrArtist.replaceAll("/",""); 
    songOrArtist = songOrArtist.replaceAll("-","");
    songOrArtist = songOrArtist.replaceAll("!",""); 
    songOrArtist = songOrArtist.replace("?","");
    songOrArtist = songOrArtist.replaceAll("#","");
    songOrArtist = songOrArtist.replaceAll("&","");
    songOrArtist = songOrArtist.replace("(","");
    songOrArtist = songOrArtist.replace(")","");
    songOrArtist = songOrArtist.toLowerCase();
    return songOrArtist;
  }
  public static String processMetro(String songOrArtist)
  {
    songOrArtist = songOrArtist.replaceAll("\'","");
    songOrArtist = songOrArtist.replace("?","");
    songOrArtist = songOrArtist.replaceAll("&","and");
    songOrArtist = songOrArtist.replaceAll("\\.","");
    songOrArtist = songOrArtist.replaceAll("!","");
    songOrArtist = songOrArtist.replaceAll("#","");
    songOrArtist = songOrArtist.replace("(","");
    songOrArtist = songOrArtist.replace(")","");
    songOrArtist = songOrArtist.replaceAll(" - ","-");
    songOrArtist = songOrArtist.toLowerCase();
    songOrArtist = songOrArtist.replaceAll("\\s","-");
    return songOrArtist;
  }
  public String getLyrics()
  { return songLyrics; }
  public String getArtist()
  { return artistText; }
  public String getSong()
  { return songText; }
  public String getBadWords()
  { return badWords; }
  public boolean hasBadWords()
  { return hasBadWords; }
  public boolean hasQWords()
  { return hasQWords; }
  //public boolean isURLInvalid()
  //{ return urlInvalid; }
  //for album processing
  //scan to "album\">", then scan to "<b>", then scan to " <\b>" for album name
  //scan to "blank\">", then scan to "</a> for song name (repeat for all songs, have user input number of songs in album)
}