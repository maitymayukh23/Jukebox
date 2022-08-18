package operation;

import entity.Playlist;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class PlaylistOperation {

    //method accepts a playlist-list and displays them
    public void displayAllPlaylists(List<Playlist> list){
        ListIterator<Playlist> iterator=list.listIterator();
        System.out.format("%-5s%-30s%n","ID","PLAYLISTS");
        while(iterator.hasNext()){
            Playlist pl=iterator.next();
            System.out.format("%-5d%-30s%n",pl.getPlaylist_id(),pl.getPlaylist_name());
        }
    }

    //method to check if the given playlist-id exists.
    public boolean validPlaylist(List<Playlist> list, int playId){
        List<Playlist> res=list.stream().filter(s->s.getPlaylist_id()==playId).collect(Collectors.toList());
        return res.size() == 1;
    }
}
