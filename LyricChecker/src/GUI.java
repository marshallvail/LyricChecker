import java.awt.event.*;  //for ActionListener, ActionEvent
import javax.swing.*;  //for JFrame, BoxLayout, JLabel, JTextField, JButton
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Insets;

public class GUI
  implements KeyListener, ActionListener
{
  public JTextField albumField;
  public JTextField artistField;
  public JTextField songField;
  private JTextArea output;
  
  public static void main(String[]args)
  {
    GUI g = new GUI();
  }
  
  public GUI()
  {
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
    form.add(new JLabel("Lyrics courtesy of AZlyrics.com"), gbc);
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
  
  public void runSongCheck()
  {
    output.setText(null);
    LyricChecker l = new LyricChecker(artistField.getText(), songField.getText());
    if(l.getSong() != null && l.getArtist() != null && l.getBadWords() != null)
    {
      String bad = l.getBadWords();
      //int indexQ = bad.indexOf("Questionable");
      output.append(l.getSong() + " by " + l.getArtist() + "\n" + bad);
      if(!l.hasBadWords() && !l.hasQWords())
        output.setBackground(Color.GREEN);
      else if(l.hasQWords() && !l.hasBadWords())
        output.setBackground(Color.YELLOW);
      else
        output.setBackground(Color.RED);
    }
    else
    {
      output.setBackground(Color.WHITE);
      output.append("URL invalid/Song not found.");
    }
  }
  public void runAlbumCheck()
  {
    output.setText(null);
    AlbumChecker a = new AlbumChecker(artistField.getText(), albumField.getText());
    output.append(a.getOut());
  }
  
  public void keyTyped(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}
}