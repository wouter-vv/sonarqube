/*
 * @Source = https://github.com/caprica/vlcj
 */
package team.margarita.streamit;

import database.DataAccessImplementation;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.medialist.MediaListItem;
import uk.co.caprica.vlcj.player.MediaDetails;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

/**
 * The movie is set accessible on a certain IP via RSTP-protocol
 * 
 * @author <stef.debrabander@student.odisee.be>, <cedric.dhooge@student.odisee.be>
 */
public class Server extends Thread {

    private MediaPlayerFactory mediaPlayerFactory;
    private HeadlessMediaPlayer mediaPlayer;
    private MediaListPlayer mediaListPlayer;
    public String genre;
    private boolean checkStop;
    List<Movie> moviesByGenre = new ArrayList<Movie>();
    
    /**
     * DEFAULT-CONSTRUCTOR
     * @param genre 
     */
    public Server(String genre, List<Movie> genreMovies) {
        this.genre = genre;
        this.moviesByGenre = genreMovies;
        new NativeDiscovery().discover();
        mediaPlayerFactory = new MediaPlayerFactory();
        mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
        mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();
    }
    
    /**
     * Return genre
     * @return 
     */
    public String getGenre() {
        return this.genre;
    }
    
    /**
     * Get MediaDetails from the current playing movie
     * @return 
     */
    public String getMediaDetails() {
        return mediaListPlayer.currentMrl();
    }
    
    /**
     * Return MediaList based on genre
     * @return 
     */
    public MediaList getMediaList() {
        
        MediaList mediaList = mediaPlayerFactory.newMediaList();
        for (int i = 0; i < this.moviesByGenre.size(); i++) {
            mediaList.addMedia(this.moviesByGenre.get(i).path, 
                        formatRtspStream(this.genre),
                        ":no-sout-rtp-sap",
                        ":no-sout-standard-sap",
                        ":sout-all",
                        ":sout-keep",
                        ":ttl=128");
        }

        return mediaList;
    }
    
    /**
     * @author Stef De Brabander, Cedric Dhooge
     * Run the server
     */
    @Override
    public void run() {
        //mediaPlayer.setSubTitleFile(genre);
        mediaListPlayer.setMediaPlayer(mediaPlayer);
        mediaListPlayer.setMediaList(getMediaList());
        mediaListPlayer.setMode(MediaListPlayerMode.LOOP);
        mediaListPlayer.play();
        
        //String path = UserRequest.get_movie_path(genre);
        //mediaPlayer.setSubTitleFile(path.substring(path.length() - 4) + ".srt");
    }
    
    /**
     * @author Stef De Brabander
     * add event listeners to the mediaPlayer
     */
    /*private void addListeners() {
        mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {           
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                if(!checkStop) {
                    String path = UserRequest.get_movie_path(genre);
                    mediaPlayer.setSubTitleFile(path.substring(path.length() - 4) + ".srt");
                } 
            }        
        });
    }*/
    
    /**
     * Create a RTSPStream configuration string
     * @param genre
     * @return 
     */
    public String formatRtspStream(String genre) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{sdp=rtsp://@");
        sb.append("10.129.35.6");
        sb.append(':');
        sb.append(8554);
        sb.append('/');
        sb.append(genre);
        sb.append("}");
        return sb.toString();
    }
}
