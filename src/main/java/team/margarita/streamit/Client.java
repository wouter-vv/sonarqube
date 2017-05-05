package team.margarita.streamit;

import entities.User;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.filter.swing.SwingFileFilterFactory;

/**
 * a client JFrame to play a movie or to connect to a livestream from a/our server
 * 
 * @author Timon
 * @author Wouter
 */
public class Client extends JPanel {
    private JLabel timeLabel;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton playButton;
    private JButton toggleMuteButton;
    private JButton connectButton;
    private JFileChooser fileChooser;
    private JButton fullScreenButton;
    private JButton movieInfoButton;
    public JFrame frame;
    public EmbeddedMediaPlayer mediaPlayer;
    private Canvas videoSurface;
    private MediaPlayerFactory mediaPlayerFactory;
    private JButton rateButton;
    private JPanel topPanel;
    public List<String> vlcArgs;
    public String Keuze;
    public String email;
    public String film;
    public String genre;
    public boolean checkStop;
    public boolean checkSubs;
    private ScheduledExecutorService eS;
    public String[] movieInfo;
    /**
     * default constructor
     */
    public Client() {
        videoSurface = new Canvas();
    }
    public void setKeuze(String keuze) {
        this.Keuze = keuze;
    }
    
    /**
     * creates the video surface and plays movie on the video surface
     * 
     * @param email
     * @param genre
     */
    public Client(String email, String genre) {
        this.email = email;
        this.genre = genre;
        movieInfo = UserRequest.get_movie_details(genre);
        new NativeDiscovery().discover();
        drawUI();
        layoutControls();    
        
        JPanel screenPanel = new JPanel();
        
        videoSurface = new Canvas();
        videoSurface.setBackground(Color.black);
        videoSurface.setSize(1000, 600);
        
        vlcArgs = new ArrayList<>();
        vlcArgs.add("--no-snapshot-preview");
        vlcArgs.add("--quiet");
        vlcArgs.add("--quiet-synchro");
        vlcArgs.add("--intf");
        vlcArgs.add("dummy");

        mediaPlayerFactory = new MediaPlayerFactory();
        mediaPlayerFactory.setUserAgent("vlcj test player");
        
        mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
        mediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(videoSurface));
        mediaPlayer.setPlaySubItems(true);
        mediaPlayer.enableOverlay(false);
        mediaPlayer.setEnableKeyInputHandling(false);
        mediaPlayer.setEnableMouseInputHandling(false);

        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.black);
        frame.add(videoSurface, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /**
         * correctly close the window
         */
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {

                if(mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                if(mediaPlayerFactory != null) {
                    mediaPlayerFactory.release();
                    mediaPlayerFactory = null;
                }
            }
        });
        
        screenPanel.add(videoSurface);
        add(screenPanel, BorderLayout.NORTH);
        addListeners();
        showFrame();
        eS = Executors.newSingleThreadScheduledExecutor();
        eS.scheduleAtFixedRate(new UpdateRunnable(mediaPlayer, this), 0L, 1L, TimeUnit.SECONDS);
        mediaPlayer.playMedia("rtsp://10.129.35.6:8554/" + this.genre);
        setKeuze("rtsp://10.129.35.6:8554/" + this.genre);
    }
    
    /**
     * shows the JFrame and adds the panel
     */
    public void showFrame() {
        frame.add(this);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
     
    /**
     * initialises all buttons
     */
    public void drawUI() {
        frame = new JFrame("Client");
        timeLabel = new JLabel("hh:mm:ss");

        stopButton = new JButton();
        stopButton.setText("Stop");

        pauseButton = new JButton();
        pauseButton.setText("Pauze");

        playButton = new JButton();
        playButton.setText("Start");

        toggleMuteButton = new JButton();
        toggleMuteButton.setText("Mute");

        connectButton = new JButton();
        connectButton.setText("Reconnect");
        
        movieInfoButton = new JButton();
        movieInfoButton.setText("Movie Info");

        // todo - add
        fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("Play");
        fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newVideoFileFilter());
        fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newAudioFileFilter());
        FileFilter defaultFilter = SwingFileFilterFactory.newMediaFileFilter();
        fileChooser.addChoosableFileFilter(defaultFilter);
        fileChooser.setFileFilter(defaultFilter);

        fullScreenButton = new JButton();
        fullScreenButton.setText("FS");

        
        rateButton = new JButton();
        rateButton.setText("Rate");
            

    }
    
    /**
     * layout of the controls
     * screen north, slider in center, buttons south
     */
    private void layoutControls() {
        setBorder(new EmptyBorder(4, 4, 4, 4));

        setLayout(new BorderLayout());

        JPanel bottomPanel = new JPanel();

        bottomPanel.setLayout(new FlowLayout());
        
        bottomPanel.add(timeLabel);
        bottomPanel.add(stopButton);
        bottomPanel.add(pauseButton);
        bottomPanel.add(playButton);
        bottomPanel.add(toggleMuteButton);
        bottomPanel.add(connectButton);
        bottomPanel.add(rateButton);
        bottomPanel.add(movieInfoButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * add eventlisteners to the buttons
     */
    private void addListeners() {
        mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
//               changeVolume(mediaPlayer.getVolume());
            }
            
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                if(!checkStop) {
                    movieInfo = UserRequest.get_movie_details(genre);
                    JOptionPane.showMessageDialog(null, "De huidige film is gedaan, indien je de volgende film wil zien druk dan op reconnect.", "InfoBox: Film is klaar", JOptionPane.INFORMATION_MESSAGE);
                    
                } 
            }        
        });
        
        movieInfoButton.addActionListener((ActionEvent e) -> {
            MovieInformation m = new MovieInformation(movieInfo);
            m.setLocationRelativeTo(null);
            m.setVisible(true);
        });
        
        stopButton.addActionListener((ActionEvent e) -> {
            checkStop=true;
            mediaPlayer.stop();
        });

        pauseButton.addActionListener((ActionEvent e) -> {       
            mediaPlayer.pause();
        });

        playButton.addActionListener((ActionEvent e) -> {
            mediaPlayer.play();
        });

        toggleMuteButton.addActionListener((ActionEvent e) -> {
            mediaPlayer.mute();
        });

        connectButton.addActionListener((ActionEvent e) -> {
            checkStop=false;
            mediaPlayer.playMedia(Keuze);
            mediaPlayer.enableOverlay(true);
            long time = mediaPlayer.getTime();
            showTime(time);
        });


        
        rateButton.addActionListener((ActionEvent e) -> {
            RatingJFrame  rjf = new RatingJFrame(email,movieInfo[0]);
            rjf.setLocationRelativeTo(null);
            rjf.setVisible(true);
        });
    }
    
    /**
     * set the timelabel
     *
     * @param timeMillisec the time the movie is currently playing in milliseconds
     */
    public void showTime(long timeMillisec) {
        String s = formatTime(timeMillisec);
        timeLabel.setText(s);
    }
    
    /**
     * formats the time to a readable string
     * 
     * @param timeMillisec
     * @return string of time in correct format
     */
    public String formatTime(long timeMillisec) {
        String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeMillisec), TimeUnit.MILLISECONDS.toMinutes(timeMillisec) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeMillisec)), TimeUnit.MILLISECONDS.toSeconds(timeMillisec) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeMillisec)));
        return s;
    }
}