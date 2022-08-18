package operation;

import entity.Podcasts;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class PodcastOperation{

    //method to display all the podcasts provided through list
    public void displayAllPodcast(List<Podcasts> podcastList){
        ListIterator<Podcasts> iterator=podcastList.listIterator();
        System.out.format("%-5s%-30s%-20s%-20s%-20s%n","ID","PODCAST","CELEBRITY","RELEASE_DATE","DURATION");
        while(iterator.hasNext()){
            Podcasts pc=iterator.next();
            System.out.format("%-5d%-30s%-20s%-20s%-20s%n",pc.getP_id(),pc.getPodcast(),pc.getCelebrity(),pc.getReleaseDate(),pc.getDuration());
        }
    }

    //method to search in playlist by celebrity name and display them
    //method returns the number of items found during the search.
    public int searchByCelebrity(List<Podcasts> podcastList, String celebrity){
        List<Podcasts> res= podcastList.stream().filter(p->p.getCelebrity().equalsIgnoreCase(celebrity)).sorted((s1,s2)->s1.getPodcast().compareTo(s2.getPodcast())).collect(Collectors.toList());
        ListIterator<Podcasts> iterator=res.listIterator();
        System.out.format("%-5s%-30s%-20s%-20s%-20s%n","ID","PODCAST","CELEBRITY","RELEASE_DATE","DURATION");
        while(iterator.hasNext()){
            Podcasts pc=iterator.next();
            System.out.format("%-5d%-30s%-20s%-20s%-20s%n",pc.getP_id(),pc.getPodcast(),pc.getCelebrity(),pc.getReleaseDate(),pc.getDuration());
        }
        return res.size();
    }

    //method to search in playlist by release date and display them
    //method returns the number of items found during the search.
    public int searchByReleaseDate(List<Podcasts> podcastList,Date date){
        List<Podcasts> res= podcastList.stream().filter(p->p.getReleaseDate().equals(date)).sorted((s1,s2)->s1.getPodcast().compareTo(s2.getPodcast())).collect(Collectors.toList());
        ListIterator<Podcasts> iterator=res.listIterator();
        System.out.format("%-5s%-30s%-20s%-20s%-20s%n","ID","PODCAST","CELEBRITY","RELEASE_DATE","DURATION");
        while(iterator.hasNext()){
            Podcasts pc=iterator.next();
            System.out.format("%-5d%-30s%-20s%-20s%-20s%n",pc.getP_id(),pc.getPodcast(),pc.getCelebrity(),pc.getReleaseDate(),pc.getDuration());
        }
        return res.size();
    }
}
