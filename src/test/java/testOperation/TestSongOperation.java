package testOperation;

import entity.Tracks;
import operation.SongOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSongOperation {
    SongOperation so;
    private Tracks s;
    private List<Tracks> songs=null;

    @BeforeEach
    public void init(){
        so=new SongOperation();
        songs= List.of(
                new Tracks(1,"One World1","artist1","genre1","album3", Time.valueOf("00:03:38"),"location1"),
                new Tracks(2,"Money for Nothing1","artist2","genre1","album3", Time.valueOf("00:02:48"),"location1"),
                new Tracks(3,"The Man’s Too Strong1","artist3","genre1","album3", Time.valueOf("00:04:35"),"location1"),
                new Tracks(4,"One World2","artist1","genre2","album2", Time.valueOf("00:03:38"),"location1"),
                new Tracks(5,"Money for Nothing2","artist2","genre2","album2", Time.valueOf("00:02:48"),"location1"),
                new Tracks(6,"The Man’s Too Strong3","artist3","genre2","album2", Time.valueOf("00:04:35"),"location1"),
                new Tracks(7,"One World3","artist1","genre3","album1", Time.valueOf("00:03:38"),"location1"),
                new Tracks(8,"Money for Nothing3","artist2","genre3","album1", Time.valueOf("00:02:48"),"location1"),
                new Tracks(9,"The Man’s Too Strong3","artist3","genre3","album1", Time.valueOf("00:04:35"),"location1")
        );
    }

    @AfterEach
    public void tearDown(){
        so=null;
    }

    @Test
    public void testSearchByArtist(){
        System.out.println("===================T1==============");
        int count=so.searchByArtis(songs,"Artist1");
        assertEquals(3,count);
        System.out.println("**********************************");
        count=so.searchByArtis(songs,"Artist2");
        assertEquals(3,count);
        System.out.println("**********************************");
        count=so.searchByArtis(songs,"Artist3");
        assertEquals(3,count);
        System.out.println("**********************************");

    }

    @Test
    public void testSearchByGenre(){
        System.out.println("===================T2==============");
        int count=so.searchByGenere(songs,"genre1");
        assertEquals(3,count);
        System.out.println("**********************************");
        count=so.searchByGenere(songs,"genre2");
        assertEquals(3,count);
        System.out.println("**********************************");
        count=so.searchByGenere(songs,"genre3");
        assertEquals(3,count);
        System.out.println("**********************************");
    }
    @Test
    public void testSearchByAlbum(){
        System.out.println("===================T3==============");
        int count=so.searchByAlbum(songs,"album1");
        assertEquals(3,count);
        System.out.println("**********************************");
        count=so.searchByAlbum(songs,"album2");
        assertEquals(3,count);
        System.out.println("**********************************");
        count=so.searchByAlbum(songs,"album3");
        assertEquals(3,count);
        System.out.println("**********************************");
    }

    @Test
    public void testForInvalidInput(){
        System.out.println("===================T4==============");
        int count=so.searchByArtis(songs,"Artist4");
        assertEquals(0,count);
        count=so.searchByGenere(songs,"genre6");
        assertEquals(0,count);
        count=so.searchByAlbum(songs,"album8");
        assertEquals(0,count);
    }

    @Test
    public void testForEmptyOrNullInput(){
        System.out.println("===================T5==============");
        int count=so.searchByArtis(songs,"");
        assertEquals(0,count);
        count=so.searchByGenere(songs,null);
        assertEquals(0,count);
        count=so.searchByAlbum(songs,"");
        assertEquals(0,count);
    }

}
