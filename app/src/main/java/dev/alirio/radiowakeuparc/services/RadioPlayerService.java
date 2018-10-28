package dev.alirio.radiowakeuparc.services;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import dev.alirio.radiowakeuparc.RadioWakeupActivity;

/**
 * author: Alirio Rivera
 * RadioPlayerService is a service that runs in the background and allows to play a radio station
 * from a URL stream which can be started or stopped.
 */
public class RadioPlayerService extends Service implements ExoPlayer.EventListener  {

    private SimpleExoPlayer player;
    private String radioName = "";
    private String radioURLStream = "";

    public RadioPlayerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            RenderersFactory renderersFactory = new DefaultRenderersFactory(getApplicationContext());
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(trackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), "ExoPlayer");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            Handler mainHandler = new Handler();

            updateRadioInformation();
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(radioURLStream),
                    dataSourceFactory,
                    extractorsFactory,
                    mainHandler,
                    null);

            player.prepare(mediaSource);
            player.setPlayWhenReady(true);

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
        player.stop();
        updateRadioInformation();
        Toast.makeText(this, "Radio Station "+ radioName +" stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
