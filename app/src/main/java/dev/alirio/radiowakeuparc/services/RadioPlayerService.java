package dev.alirio.radiowakeuparc.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import java.io.IOException;

import dev.alirio.radiowakeuparc.RadioWakeupActivity;
import wseemann.media.FFmpegMediaPlayer;

/**
 * author: Alirio Rivera
 * RadioPlayerService is a service that runs in the background and allows to play a radio station
 * from a URL stream which can be started or stopped.
 */
public class RadioPlayerService extends Service {

    private FFmpegMediaPlayer mediaPlayer;
    private String radioName = "";
    private String radioURLStream = "";

    public RadioPlayerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer = new FFmpegMediaPlayer();
        mediaPlayer.setOnPreparedListener(new FFmpegMediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(FFmpegMediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnErrorListener(new FFmpegMediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(FFmpegMediaPlayer mp, int what, int extra) {
                mediaPlayer.release();
                return false;
            }
        });

        try {
            mediaPlayer.setDataSource(radioURLStream);
            mediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Service.START_NOT_STICKY;
    }


    private void updateRadioInformation(){
        this.radioURLStream = RadioWakeupActivity.DEFAULT_RADIO_STATION.getUrl();
        this.radioName = RadioWakeupActivity.DEFAULT_RADIO_STATION.getName();
    }

    @Override
    public void onCreate() {
        updateRadioInformation();
        Toast.makeText(this, "Playing: " + radioName, Toast.LENGTH_LONG).show();
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        updateRadioInformation();
        Toast.makeText(this, "Radio Station "+ radioName +" stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }
}
