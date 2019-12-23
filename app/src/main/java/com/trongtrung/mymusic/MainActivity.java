package com.trongtrung.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtName, txtInfo, curTime, maxTime;
    SeekBar seekBar;
    ImageButton btnPlay, btnPrevious, btnNext, btnStop;

    ArrayList<Song> listOfSongs;
    MediaPlayer media;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Assign();
        addSong();

        media = MediaPlayer.create(MainActivity.this, listOfSongs.get(position).getFile());
        txtName.setText(listOfSongs.get(position).getName());
        txtInfo.setText(listOfSongs.get(position).getSinger());

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

            }
        });

    }



    private void Assign()
    {
        txtName = findViewById(R.id.SongName);
        txtInfo = findViewById(R.id.SongSinger);
        curTime = findViewById(R.id.CurrentTime);
        maxTime = findViewById(R.id.MaxTime);
        seekBar = findViewById(R.id.SeekBar);
        btnPlay = findViewById(R.id.ButtonPlay);
        btnNext = findViewById(R.id.ButtonNext);
        btnPrevious = findViewById(R.id.ButtonPrevious);
        btnStop = findViewById(R.id.ButtonStop);

    }

    private void addSong() {
        listOfSongs = new ArrayList<>();

        listOfSongs.add(new Song("Alone", "Alan Walker", R.raw.alone));
        listOfSongs.add(new Song("Faded", "Alan Walker", R.raw.faded));
        listOfSongs.add(new Song("Firework", "Katy Perry", R.raw.firework));
        listOfSongs.add(new Song("If I Can't Have You", "Shawn Mendes", R.raw.if_i_cant_have_you));
        listOfSongs.add(new Song("Into The Unknown", "Idina Menzel", R.raw.into_the_unknown));
        listOfSongs.add(new Song("On My Way", "Alan Walker, Sabrina Carpenter, Farruko", R.raw.on_my_way));
        listOfSongs.add(new Song("Señorita", "Shawn Mendes, Camila Cabello", R.raw.senorita));
        listOfSongs.add(new Song("Something Just Like This", "The Chainsmokers, Coldplay", R.raw.something_just_like_this));
    }
}
