import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import musixmatch.MusixMatch;
import musixmatch.Playlist;
import musixmatch.Track;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI implements KeyListener, ActionListener {
	public JTextField albumField;
	public JTextField artistField;
	public JTextField songField;
	private JTextArea output;
	private ArrayList<String> badWords;
	private ArrayList<String> qWords;

	public static void main(String[]args) {
		GUI g = new GUI();
		g.hashCode();
	}

	public GUI() {
		badWords = new ArrayList<String>(Arrays.asList("asshole","bitch","cocksucker","cunt","dick","fuck","nigga","nigger","piss","pussy","shit"));
		qWords = new ArrayList<String>(Arrays.asList(" ass ","bastard","damn","hell","sex"));
		//make a window
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Lyric Checker");
		JPanel form = new JPanel(new GridBagLayout());
		form.setBorder(BorderFactory.createEmptyBorder(7, 7, 5, 10)); //top left bottom right
		//frame.getContentPane().setLayout(new GridBagLayout());

		JLabel artist = new JLabel("Artist: ");
		JLabel song = new JLabel("Song: ");
		JLabel album = new JLabel("Album: ");

		artistField = new JTextField(40);
		songField = new JTextField(40);
		albumField = new JTextField(40);

		//JButton button = new JButton("Go!");

		output = new JTextArea(10, 2);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		output.setEditable(false);
		JScrollPane pane = new JScrollPane();
		pane.getViewport().add(output);


		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2,2,2,2);  // this statement added.
		gbc.gridx = 0;

		gbc.gridy = 0;
		form.add(artist, gbc);

		gbc.gridy = 1;
		form.add(album, gbc);

		gbc.gridy = 2;
		form.add(song, gbc);

		//    gbc.gridx = 1;
		//    gbc.gridy = 4;
		//    form.add(credit, gbc);

		gbc.gridx = 1;

		gbc.gridy = 0;
		form.add(artistField, gbc);

		gbc.gridy = 1;
		form.add(albumField, gbc);

		gbc.gridy = 2;
		form.add(songField, gbc);

		gbc.gridy = 4;
		//form.add(button, gbc);

		//gbc.gridy = 3; //5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;

		form.add(pane, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		form.add(new JLabel("Lyrics courtesy of Musixmatch.com"), gbc);
		gbc.gridy = 3;
		form.add(new JLabel("Input artist & album or artist & song and press enter."), gbc);
		//button.addActionListener(this);
		artistField.addKeyListener(this);
		albumField.addKeyListener(this);
		songField.addKeyListener(this);
		//button.setActionCommand("button1");
		frame.add(form);
		frame.pack();  //determine best size for window
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);  //show the window
	}

	public void actionPerformed(ActionEvent e)
	{
		//button was pressed
		//    if (e.getActionCommand().equals("button1"))
		//    {
		//      run();
		//    }
		//    else
		//      output.append("URL invalid/Song not found.");
	}

	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) 
		{ 
			if(albumField.getText().equals("") && !artistField.getText().equals("") && !songField.getText().equals(""))
			{
				runSongCheck();
				songField.requestFocusInWindow();
				songField.selectAll();
			}
			else if(songField.getText().equals("") && !albumField.getText().equals("") && !artistField.getText().equals(""))
			{
				output.setBackground(Color.WHITE);
				runAlbumCheck();
				albumField.requestFocusInWindow();
				albumField.selectAll();
			}
			else
			{
				output.setBackground(Color.WHITE);
				output.setText(null);
				output.append("Do not fill out all three fields.");
			}
		}
	}

	public void runSongCheck() {
		try {
			output.setText("");
			Track t = MusixMatch.trackSearch(songField.getText(), artistField.getText());
			output.append(t.getName());
			LyricChecker lc = new LyricChecker(t, badWords, qWords);
			if (t.isExplicit()) output.append(" [Explicit]");
			else {
				lc.checkLyrics();
				if (lc.hasBadWords()) output.append(" [Dirty]");
				else if (lc.hasQWords()) output.append(" [Questionable]");
				else output.append(" [Clean]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void runAlbumCheck() {
		try {
			output.setText("");
			Playlist album = MusixMatch.albumSearch(albumField.getText(), artistField.getText());
			if (album == null)  {
				output.setText("No such album.");
				return;
			}
			for (Track t : album) {
				output.append(t.getName());
				LyricChecker lc = new LyricChecker(t, badWords, qWords);
				if (t.isExplicit()) output.append(" [Explicit]");
				else {
					lc.checkLyrics();
					if (lc.hasBadWords()) output.append(" [Dirty]");
					else if (lc.hasQWords()) output.append(" [Questionable]");
					else output.append(" [Clean]");
				}
				output.append("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}