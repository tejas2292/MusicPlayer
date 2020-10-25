package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    TextView startTime, endTime;
    ImageButton pause, previousSong, nextSong, forward, replay;
    int count = 0, currentPosition, totalDuration;
    SeekBar seekBar;
    TextView tvSongName, tvSongBy;
    Thread updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        pause = findViewById(R.id.pausebtn);
        previousSong = findViewById(R.id.backBtn);
        nextSong = findViewById(R.id.nextBtn);
        startTime = findViewById(R.id.tvStartTime);
        endTime = findViewById(R.id.tvEndTime);
        seekBar = findViewById(R.id.seekBarTime);
        tvSongName = findViewById(R.id.songName);
        tvSongBy = findViewById(R.id.songBy);
        forward = findViewById(R.id.forward10);
        replay = findViewById(R.id.replay10);
        startSong();


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setMax(player.getDuration());
                if (player.isPlaying()) {
                    pause.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24);
                    player.pause();
                } else {
                    pause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    player.start();
                }


            }
        });

        previousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                if(count==0){
                    count = 3;
                    startSong();
                }
                else if(count==1){
                    count = 0;
                    startSong();
                }
                else if(count==2){
                    count = 1;
                    startSong();
                }
                else if(count==3){
                    count = 2;
                    startSong();
                }
            }
        });

        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                if(count==0){
                    count = 1;
                    startSong();
                }
                else if(count==1){
                    count = 2;
                    startSong();
                }
                else if(count==2){
                    count = 3;
                    startSong();
                }
                else if(count==3){
                    count = 0;
                    startSong();
                }
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(currentPosition+10000);
            }
        });

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(currentPosition-10000);
            }
        });
    }

    private void startSong() {

        updateSeekBar = new Thread() {
            @Override
            public void run() {
                totalDuration = player.getDuration();
                currentPosition = 0;

                while (currentPosition <= totalDuration) {
                    try {
                        sleep(1000);
                        currentPosition = player.getCurrentPosition();
                        seekBar.setProgress(currentPosition);

                    } catch (Exception ignored) {

                    }
                }
            }
        };

        if (player != null) {
            player.stop();
            player.release();
        }

        if(count==0){
            player = MediaPlayer.create(MainActivity.this, R.raw.yaalgar);
            pause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24);
            player.start();
            tvSongName.setText("YALGAAR");
            tvSongBy.setText("CARRYMINATI X Wily Frenzy");
            tvSongName.setSelected(true);
        }
        else if(count == 1){
            player = MediaPlayer.create(MainActivity.this, R.raw.believer);
            pause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24);
            player.start();
            tvSongBy.setText("Imagine Dragons");
            tvSongName.setText("Believer");
            tvSongName.setSelected(true);
        }
        else if(count == 2){
            player = MediaPlayer.create(MainActivity.this, R.raw.magenta);
            pause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24);
            player.start();
            tvSongName.setText("Magenta Riddim");
            tvSongBy.setText("DJ Snake");
            tvSongName.setSelected(true);
        }
        else if(count == 3){
            player = MediaPlayer.create(MainActivity.this, R.raw.cool);
            pause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24);
            player.start();
            tvSongName.setText("Cool Energetic Song");
            tvSongBy.setText("NA");
            tvSongName.setSelected(true);
        }

        seekBar.setMax(player.getDuration());

        updateSeekBar.start();

        String duration = millisecondsToString(player.getDuration());
        endTime.setText(duration);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String duration1 = millisecondsToString(progress);
                startTime.setText(duration1);

                if (progress == player.getDuration()) {
                    player.reset();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
            }
        });

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                player.stop();
                if(count==0){
                    count = 1;
                    startSong();
                }
                else if(count==1){
                    count = 2;
                    startSong();
                }
                else if(count==2){
                    count = 3;
                    startSong();
                }
                else if(count==3){
                    count = 0;
                    startSong();
                }
            }
        });

    }

    public String millisecondsToString(int time) {
        String elapsedTime = "";
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        elapsedTime = minutes + ":";
        if (seconds < 10) {
            elapsedTime += "0";
        }
        elapsedTime += seconds;

        return elapsedTime;
    }
}