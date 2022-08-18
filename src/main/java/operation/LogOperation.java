package operation;

import entity.Log;
import entity.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class LogOperation {

    //method accepts song-list and podcast-list, then displays them.
    //this method also extracts the song/podcast name along with their location.
    public List<Record> displayByPlaylist(List<Log> slist, List<Log> plist){
       List<Record> recordList=new ArrayList<>();
        ListIterator<Log> iterator= slist.listIterator();
        System.out.format("%-10s%-30s%-20s%-20s%-20s%-20s%n","Log_Id","Song","Artist","Genre","Album","Duration");
        while(iterator.hasNext()){
            Log l=iterator.next();
            recordList.add(new Record(l.getTracks().getSong(),l.getTracks().getLocation()));
            System.out.format("%-10d%-30s%-20s%-20s%-20s%-20s%n",l.getLogId(),l.getTracks().getSong(),l.getTracks().getArtist(),l.getTracks().getGenre(),l.getTracks().getAlbum(),l.getTracks().getDuration());
        }
        System.out.println();
        iterator= plist.listIterator();
        System.out.format("%-10s%-30s%-20s%-20s%-20s%n","Log_Id","Podcast","Celebrity","Publishing Date","Duration");
        while(iterator.hasNext()){
            Log l=iterator.next();
            recordList.add(new Record(l.getPod().getPodcast(),l.getPod().getLocation()));
            System.out.format("%-10d%-30s%-20s%-20s%-20s%n",l.getLogId(),l.getPod().getPodcast(),l.getPod().getCelebrity(),l.getPod().getReleaseDate(),l.getPod().getDuration());
        }
        return recordList;
    }
}
