package operation;

import databaseConnection.Connector;
import entity.Tracks;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class SongOperation extends Connector {

    //method to display all the songs provided through a list
    public void displayAllSongs(List<Tracks> songList) {
        ListIterator<Tracks> iterator=songList.listIterator();
        System.out.format("%-5s%-30s%-20s%-20s%-20s%-20s%n","ID","SONG","ARTIST","GENRE","ALBUM","DURATION");
        while(iterator.hasNext()){
            Tracks ts=iterator.next();
            System.out.format("%-5d%-30s%-20s%-20s%-20s%-20s%n",ts.getT_id(),ts.getSong(),ts.getArtist(),ts.getGenre(),ts.getAlbum(),ts.getDuration());
        }
    }

    //method to search by artist name, display them, and return the number of results found.
    public int searchByArtis(List<Tracks>songList,String artist){
        List<Tracks> res= songList.stream().filter(p->p.getArtist().equalsIgnoreCase(artist)).sorted((s1,s2)->s1.getSong().compareTo(s2.getSong())).collect(Collectors.toList());
        ListIterator<Tracks> iterator=res.listIterator();
        System.out.format("%-5s%-30s%-20s%-20s%-20s%-20s%n","ID","SONG","ARTIST","GENRE","ALBUM","DURATION");
        while(iterator.hasNext()){
            Tracks ts=iterator.next();
            System.out.format("%-5d%-30s%-20s%-20s%-20s%-20s%n",ts.getT_id(),ts.getSong(),ts.getArtist(),ts.getGenre(),ts.getAlbum(),ts.getDuration());
        }
        return res.size();
    }

    //method to search by genre, display them, and return the number of results found.
    public int searchByGenere(List<Tracks>songList,String genre){
        List<Tracks> res= songList.stream().filter(p->p.getGenre().equalsIgnoreCase(genre)).sorted((s1,s2)->s1.getSong().compareTo(s2.getSong())).collect(Collectors.toList());
        ListIterator<Tracks> iterator=res.listIterator();
        System.out.format("%-5s%-30s%-20s%-20s%-20s%-20s%n","ID","SONG","ARTIST","GENRE","ALBUM","DURATION");
        while(iterator.hasNext()){
            Tracks ts=iterator.next();
            System.out.format("%-5d%-30s%-20s%-20s%-20s%-20s%n",ts.getT_id(),ts.getSong(),ts.getArtist(),ts.getGenre(),ts.getAlbum(),ts.getDuration());
        }
        return res.size();
    }

    //method to search by album name, display them, and return the number of results found.
    public int searchByAlbum(List<Tracks>songList,String album){
        List<Tracks> res= songList.stream().filter(p->p.getAlbum().equalsIgnoreCase(album)).sorted((s1,s2)->s1.getSong().compareTo(s2.getSong())).collect(Collectors.toList());
        ListIterator<Tracks> iterator=res.listIterator();
        System.out.format("%-5s%-30s%-20s%-20s%-20s%-20s%n","ID","SONG","ARTIST","GENRE","ALBUM","DURATION");
        while(iterator.hasNext()){
            Tracks ts=iterator.next();
            System.out.format("%-5d%-30s%-20s%-20s%-20s%-20s%n",ts.getT_id(),ts.getSong(),ts.getArtist(),ts.getGenre(),ts.getAlbum(),ts.getDuration());
        }
        return res.size();
    }
}
