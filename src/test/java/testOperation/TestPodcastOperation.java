package testOperation;

import entity.Podcasts;
import entity.Tracks;
import operation.PodcastOperation;
import operation.SongOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPodcastOperation {
    PodcastOperation po;
    private Podcasts p;
    private List<Podcasts> songs=null;

    @BeforeEach
    public void init(){
        po=new PodcastOperation();
        songs= List.of(
                new Podcasts(1,"One World1","artist1", Date.valueOf("2021-05-04"), Time.valueOf("00:03:38"),"location1"),
                new Podcasts(2,"Money for Nothing1","artist2",Date.valueOf("2019-04-25"),Time.valueOf("00:02:48"),"location1"),
                new Podcasts(3,"The Man’s Too Strong1","artist3",Date.valueOf("2019-04-25"), Time.valueOf("00:04:35"),"location1"),
                new Podcasts(4,"One World2","artist1",Date.valueOf("2018-01-01"),Time.valueOf("00:03:38"),"location1"),
                new Podcasts(5,"Money for Nothing2","celebrity1",Date.valueOf("2021-05-04"), Time.valueOf("00:02:48"),"location1"),
                new Podcasts(6,"The Man’s Too Strong3","celebrity2",Date.valueOf("2018-10-12"), Time.valueOf("00:04:35"),"location1"),
                new Podcasts(7,"One World3","celebrity2",Date.valueOf("2018-10-12"), Time.valueOf("00:03:38"),"location1"),
                new Podcasts(8,"Money for Nothing3","celebrity1",Date.valueOf("2014-05-05"), Time.valueOf("00:02:48"),"location1"),
                new Podcasts(9,"The Man’s Too Strong3","celebrity1",Date.valueOf("2015-03-18"), Time.valueOf("00:04:35"),"location1")
        );
    }

    @AfterEach
    public void tearDown(){
        po=null;
    }

    @Test
    public void testSearchByCelebrity(){
        System.out.println("===================T1==============");
        int count=po.searchByCelebrity(songs,"Artist1");
        assertEquals(2,count);
        System.out.println("**********************************");
        count=po.searchByCelebrity(songs,"celebrity1");
        assertEquals(3,count);

    }

    @Test
    public void testSearchByReleaseDate(){
        System.out.println("===================T2==============");
        int count=po.searchByReleaseDate(songs,Date.valueOf("2021-05-04"));
        assertEquals(2,count);
        System.out.println("**********************************");
        count=po.searchByReleaseDate(songs,Date.valueOf("2015-03-18"));
        assertEquals(1,count);
    }

    @Test
    public void testForInvalidInput(){
        System.out.println("===================T3==============");
        int count=po.searchByCelebrity(songs,"celebrity4");
        assertEquals(0,count);
        count=po.searchByReleaseDate(songs,Date.valueOf("1990-03-18"));
        assertEquals(0,count);
    }

    @Test
    public void testForEmptyOrNullInput(){
        System.out.println("===================T4==============");
        int count=po.searchByCelebrity(songs,"");
        assertEquals(0,count);
        count=po.searchByCelebrity(songs,null);
        assertEquals(0,count);
    }

}
