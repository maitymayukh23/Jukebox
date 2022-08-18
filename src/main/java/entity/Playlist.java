package entity;

public class Playlist {
    private int playlist_id;
    private String playlist_name;

    public Playlist(int playlist_id, String playlist_name) {
        this.playlist_id = playlist_id;
        this.playlist_name = playlist_name;
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public String getPlaylist_name() {
        return playlist_name;
    }

}
