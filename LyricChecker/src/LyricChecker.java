import java.util.ArrayList;
import musixmatch.Track;

public class LyricChecker {
	private Track track;
	private ArrayList<String> bwc;
	private ArrayList<String> qwc;
	private ArrayList<String> bw;
	private ArrayList<String> qw;
	
	public LyricChecker (Track t, ArrayList<String> bWords, ArrayList<String> qWords) {
		track = t;
		bw = bWords;
		qw = qWords;
		bwc = new ArrayList<String>();
		qwc = new ArrayList<String>();
	}

	public void checkLyrics () {
		String lyrics = track.getLyrics().toLowerCase();
		for(int i = 0; i < bw.size(); i++) {
			String cbw = bw.get(i);
			if (!bwc.contains(cbw)) {
				if (lyrics.indexOf(cbw) > -1) bwc.add(cbw);
			}
		}
		for (int i = 0; i < qw.size(); i++) {
			String cqw = qw.get(i);
			if (!qwc.contains(cqw)) {
				if (lyrics.indexOf(cqw) > -1) qwc.add(cqw);
			}
		}
	}

	public void addBWord(String s) {
		bw.add(s.toLowerCase());
	}

	public void removeBWord(String s) {
		bw.remove(s.toLowerCase());
	}

	public void addQWord(String s) {
		qw.add(s.toLowerCase());
	}

	public void removeQWord(String s) {
		qw.remove(s.toLowerCase());
	}
	
	public ArrayList<String> foundBadWords () {
		return bwc;
	}
	
	public boolean hasBadWords() {
		return bwc.size() > 0;
	}
	
	public boolean hasQWords() {
		return qwc.size() > 0;
	}

}