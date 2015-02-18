import java.util.Scanner;
import java.util.LinkedList;

public class AlbumChecker
{
  private String albumText;
  private int numSongs;
  private LinkedList<String> songs;
  public String out;
  //for album processing
  //scan to "album\">", then scan to "<b>", then scan to " <\b>" for album name
  //scan to "blank\">", then scan to "</a> for song name (repeat for all songs, have user input number of songs in album)
  public AlbumChecker(String artist, String album) //user inputted album name
  {
    out = "";
    album  = processLastFM(album);    // makes the album and artist name all lowercase
    String azArtist = artist;
    artist = processLastFM(artist);
    String lastFMPage;
    Scanner scan;
    try
    {
      lastFMPage = URLConnectionReader.getText("http://www.last.fm/music/" + artist + "/" + album);
      scan = new Scanner(lastFMPage);
      System.out.println(lastFMPage);
      
      scan.useDelimiter("name\">");
      scan.next();
      scan.useDelimiter("</h1>");
      albumText = scan.next().substring(22); //remove white space at end
      System.out.println(albumText);
      
      scan.useDelimiter("Running length");
      scan.next();
      scan.useDelimiter("<dd>");
      scan.next();
      scan.useDelimiter(" ");
      numSongs = Integer.parseInt(scan.next().substring(4));
      
      while(numSongs != 0)
      {
        scan.useDelimiter("name\">");
        scan.next();
        scan.useDelimiter("</span>");
        String currSong = scan.next().substring(6); //& comes out as &amp; have to fix this, probably the same for the album name
        int indexOfAmp = currSong.indexOf("&amp;");
        if(indexOfAmp != -1)
          currSong = currSong.substring(0, indexOfAmp+1) + currSong.substring(indexOfAmp+5);
        System.out.println(azArtist+" "+currSong);
        LyricChecker l = new LyricChecker(azArtist, currSong);
        Thread.sleep(1000); // do nothing for 1000 miliseconds (1 second)
        out = out + l.getSong() + " by " + l.getArtist() + "\n" + l.getBadWords() + "\n\n";
        numSongs--;
      }
      out = out.substring(0, out.length()-2);
    }
    catch(Exception ex)
    {
      return;
    }
  }
  public String processLastFM(String artistOrAlbum)
  {
    artistOrAlbum = artistOrAlbum.replaceAll("\\s","+");
    artistOrAlbum = artistOrAlbum.replaceAll("/","%2F");
    return artistOrAlbum;
  }
  public int getNumSongs()
  { return numSongs; }
  public String getOut()
  { return out; }
  public String getAlbumText()
  { return albumText; }
  public static void main(String[]args)
  {
    AlbumChecker a = new AlbumChecker("spoon", "ga ga ga ga ga");
    System.out.print(a.getOut());
  }
}
  