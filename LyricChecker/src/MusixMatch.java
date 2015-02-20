import java.net.URLEncoder;
import json.JSONArray;
import json.JSONNumber;
import json.JSONObject;
import json.JSONParser;

public class MusixMatch {
	private static final String api_base = "http://api.musixmatch.com/ws/1.1/";
	private static final String api_key = "7e4f8d8e45c998aae965023755ffafd3";
	
	private JSONObject apiFetch (String url) throws Exception {
		String feedback = URLConnectionReader.getText(api_base+url);
		JSONParser p = new JSONParser(feedback);
		JSONObject message = p.object().getObject("message");
		JSONNumber status = message.getObject("header").getNumber("status_code");
		if (status.getValue() != 200) throw new MusixMatchException("API lookup did not return successfully.");
		return message.getObject("body");
	}
		
	public boolean explicit (int id) throws Exception {
		JSONObject data = apiFetch("track.lyrics.get?apikey="+api_key+"&track_id="+id+"&format=json").getObject("lyrics");
		JSONNumber num = data.getNumber("explicit");
		return num.getValue() > 0;
	}
	
	public int songSearch (String song, String artist) throws Exception {
		JSONObject data = apiFetch("track.search?apikey="+api_key+"&q_track="+URLEncoder.encode(song, "UTF-8")+"&q_artist="+URLEncoder.encode(artist, "UTF-8")+"&page_size=1&format=json");
		JSONArray tracklist = data.getArray("track_list");
		if (tracklist.size() <= 0) return -1;
		JSONNumber firstID = tracklist.getObject(0).getObject("track").getNumber("track_id");
		return (int) firstID.getValue();
	}
	
	private int albumId (JSONObject album) {
		return (int)album.getObject("album").getNumber("album_id").getValue();
	}
	
	private String albumName (JSONObject album) {
		return album.getObject("album").getString("album_name").getValue();
	}
	
	public int[] albumSearch (String album, String artist) throws Exception {
		JSONObject artistSearch = apiFetch("artist.get?apikey="+api_key+"&q_artist="+URLEncoder.encode(artist, "UTF-8")+"&page_size=1&format=json");
		JSONArray artistList = artistSearch.getArray("artist_list");
		if (artistList.size() <= 0) return null;
		JSONNumber artistIDNum = artistList.getObject(0).getObject("artist").getNumber("artist_id");
		int artistId = (int)artistIDNum.getValue();
		JSONObject albumsData = apiFetch("artist.albums.get?apikey="+api_key+"&artist_id="+artistId+"&page_size="+Integer.MAX_VALUE+"&format=json");
		JSONArray albumList = albumsData.getArray("album_list");
		int albumId = 0;
		for (int i = 0; i < albumList.size(); i++) {
			if (albumName(albumList.getObject(i)).equalsIgnoreCase(album)) {
				albumId = albumId(albumList.getObject(i));
				break;
			}
		}
		if (albumId <= 0) return null;
		JSONObject tracksSearch = apiFetch("album.tracks.get?apikey="+api_key+"&album_id="+albumId+"&page_size="+Integer.MAX_VALUE+"&format=json");
		JSONArray tracksList = tracksSearch.getArray("tracks_list");
		int[] tracks = new int[tracksList.size()];
		for (int i = 0; i < tracks.length; i++) {
			tracks[i] = (int)tracksList.getObject(i).getObject("track").getNumber("track_id").getValue();
		}
		return tracks;
	}
	
}
