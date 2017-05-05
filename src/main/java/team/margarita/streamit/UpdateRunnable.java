/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.margarita.streamit;

import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 * class to update the time while the movie is playing
 * 
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class UpdateRunnable implements Runnable {

        private final MediaPlayer mediaPlayer;
        private Client client;

        public UpdateRunnable(MediaPlayer mediaPlayer, Client client) {
            this.mediaPlayer = mediaPlayer;
            this.client = client;
        }

        /**
         * runnable class that runs in the background
         */
        @Override
        public void run() {
            final long timeMillisec = mediaPlayer.getTime();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer.isPlaying()) {
                        client.showTime(timeMillisec);
                    }
                }
            });
        }
    }
