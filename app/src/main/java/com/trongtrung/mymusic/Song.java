package com.trongtrung.mymusic;
/**
 * Created by NguyenTrongTrung on 23 December 2019
 */
public class Song {
    private String name;
    private String singer;
    private int file;

    public Song(String name, String singer, int file) {
        this.name = name;
        this.singer = singer;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public int getFile() {
        return file;
    }
}
