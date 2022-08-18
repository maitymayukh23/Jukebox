package entity;

import java.sql.Time;

public class Tracks {
    private int t_id;
    private String song;
    private String artist;
    private String genre;
    private String album;
    private Time duration;
    private String location;


    public Tracks(int t_id, String song, String artist, String genre, String album, Time duration, String location) {
        this.t_id = t_id;
        this.song = song;
        this.artist = artist;
        this.genre = genre;
        this.album = album;
        this.duration = duration;
        this.location = location;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
