package com.trongtrung.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtName, txtInfo, curTime, maxTime;
    SeekBar seekBar;
    ImageButton btnPlay, btnPrevious, btnNext, btnRepeat;
    ImageView disc;
    ArrayList<Song> listOfSongs;
    MediaPlayer media;
    int position = 0;
    SimpleDateFormat formatTime;
    Animation animation;
    byte repeatButtonState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        formatTime = new SimpleDateFormat("mm:ss");
        assignVariables();
        addSong();
        initializeMediaPlayer();
        animation = AnimationUtils.loadAnimation(this,R.anim.rotate_dics);
        disc.startAnimation(animation);
        //event for Play Button
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (media.isPlaying())
                {
                    btnPlay.setImageResource(R.drawable.play);
                    media.pause();
                }
                else
                {
                    btnPlay.setImageResource(R.drawable.pause);
                    media.start();
                }
                setMaxTime();
                updateTimeSong();

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNextSong();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (media.getCurrentPosition() <= 3000)
                {                    position--;
                    if (position < 0) position = listOfSongs.size() - 1;
                    changeSong();
                }
                else {
                   changeSong();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                media.seekTo(seekBar.getProgress());
            }
        });


        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (repeatButtonState){
                    case 0:
                        btnRepeat.setImageResource(R.drawable.repeat_1);
                        repeatButtonState++;
                        break;
                    case 1:
                        btnRepeat.setImageResource(R.drawable.repeat_2);
                        repeatButtonState++;
                        break;
                    default:
                        btnRepeat.setImageResource(R.drawable.repeat_0);
                        repeatButtonState=0;
                        break;
                }

            }
        });
    }

    private void changeNextSong()
    {
        position++;
        if (position >= listOfSongs.size()) position = 0;
        changeSong();
    }
    private void assignVariables()
    {
        txtName = findViewById(R.id.SongName);
        txtInfo = findViewById(R.id.SongSinger);
        curTime = findViewById(R.id.CurrentTime);
        maxTime = findViewById(R.id.MaxTime);
        seekBar = findViewById(R.id.SeekBar);
        btnPlay = findViewById(R.id.ButtonPlay);
        btnNext = findViewById(R.id.ButtonNext);
        btnPrevious = findViewById(R.id.ButtonPrevious);
        btnRepeat = findViewById(R.id.ButtonRepeat);
        disc = findViewById(R.id.Disc);
        repeatButtonState = 0;


    }

    //add song to list
    private void addSong() {
        listOfSongs = new ArrayList<>();
        listOfSongs.add(new Song("All Is Found", "Evan Rachel Wood", R.raw.all_is_found));
        listOfSongs.add(new Song("Alone", "Alan Walker", R.raw.alone));
        listOfSongs.add(new Song("Faded", "Alan Walker", R.raw.faded));
        listOfSongs.add(new Song("Firework", "Katy Perry", R.raw.firework));
        listOfSongs.add(new Song("If I Can't Have You", "Shawn Mendes", R.raw.if_i_cant_have_you));
        listOfSongs.add(new Song("Into The Unknown", "Idina Menzel", R.raw.into_the_unknown));
        listOfSongs.add(new Song("On My Way", "Alan Walker, Sabrina Carpenter, Farruko", R.raw.on_my_way));
        listOfSongs.add(new Song("Señorita", "Shawn Mendes, Camila Cabello", R.raw.senorita));
        listOfSongs.add(new Song("Something Just Like This", "The Chainsmokers, Coldplay", R.raw.something_just_like_this));
    }

    private void initializeMediaPlayer()
    {
        media = MediaPlayer.create(MainActivity.this, listOfSongs.get(position).getFile());
        txtName.setText(listOfSongs.get(position).getName());
        txtInfo.setText(listOfSongs.get(position).getSinger());
        setMaxTime();
        updateTimeSong();
    }

    private void changeSong()
    {
        if (media.isPlaying())
        {
            media.stop();
            media.release();
        }
        initializeMediaPlayer();
        media.start();
        btnPlay.setImageResource(R.drawable.pause);

    }

    private void setMaxTime()
    {

        maxTime.setText(formatTime.format(media.getDuration()));

        seekBar.setMax(media.getDuration());
    }


    private void updateTimeSong()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                curTime.setText(formatTime.format(media.getCurrentPosition()));
                seekBar.setProgress(media.getCurrentPosition());
                media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (repeatButtonState == 0)
                        {
                            media.stop();
                            media.release();
                            initializeMediaPlayer();
                            btnPlay.setImageResource(R.drawable.play);
                        }
                        else if (repeatButtonState == 1)
                        {
                            changeNextSong();
                        }
                        else
                        {
                            position--;
                            changeNextSong();
                        }

                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }


}
