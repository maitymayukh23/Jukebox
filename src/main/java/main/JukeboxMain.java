package main;

import dao.*;
import databaseConnection.Connector;
import entity.*;
import entity.Record;
import operation.*;
import userException.InvalidException;
import wavPlayer.PlayerConsole;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class JukeboxMain {

    UserDao ud = new UserDao();
    SongDao sd = new SongDao();
    PodcastDao pd = new PodcastDao();
    PlaylistDao pld = new PlaylistDao();
    LogDao ld = new LogDao();

    UserOperation uo = new UserOperation();
    SongOperation so = new SongOperation();
    PodcastOperation po = new PodcastOperation();
    PlaylistOperation plo = new PlaylistOperation();
    LogOperation lo = new LogOperation();

    List<Tracks> songList;
    List<Podcasts> podcastList;
    List<Users> usersList;
    List<Playlist> playlist;
    List<Log> slist;//for songs
    List<Log> plist;//for podcast

    int uID=0;
    int playId=0;

    Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        int choice1, choice2;

        Scanner s = new Scanner(System.in);
        System.out.println("WELCOME");
        JukeboxMain jbm = new JukeboxMain();
        try {
            jbm.setup();
            aa:
            do {
                System.out.println("1.NEW USER\t2.EXISTING USER\t0.EXIT");
                choice1 = s.nextInt();
                s.nextLine();
                switch (choice1) {
                    case 1:
                        jbm.newUserEntry();
                        break;
                    case 2:
                        if (jbm.existingUserEntry()) {
                            do {
                                System.out.println("3.My Playlists\t4.Add Playlist\t5.Add in Existing Playlist\t0.Exit\t-1.Prev. Menu");
                                choice2 = s.nextInt();
                                s.nextLine();
                                switch (choice2) {
                                    case 3:
                                        jbm.myPlaylist();
                                        break;
                                    case 4:
                                        jbm.addPlaylistEntry();
                                        break;
                                    case 5:
                                        jbm.addInExistingPlaylistEntry();
                                        break;
                                    case 0:
                                        break aa;

                                }

                            } while (choice2 != -1);
                        } else
                            throw new InvalidException("Invalid user id/password");
                        break;
                }
            } while (choice1 != 0);
            Connector.closeConnection();
        } catch (SQLException e) {
            e.getStackTrace();
        } catch (InvalidException | UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setup() throws SQLException {
        songList = sd.readAllSongs();
        podcastList = pd.readAllPodcasts();
        so.displayAllSongs(songList);
        System.out.println();
        po.displayAllPodcast(podcastList);
    }

    private void newUserEntry() throws SQLException {
        System.out.println("User Name?");
        String name = s.nextLine();
        System.out.println("Set Password(Max:20 characters)");
        String password = s.nextLine();
        ud.newUser(new Users(name, password));
    }

    private boolean existingUserEntry() throws SQLException {
        System.out.println("User_id?");
        uID = s.nextInt();
        s.nextLine();
        System.out.println("Password?");
        String password = s.nextLine();
        usersList = ud.readAllUsers();
        return uo.validUser(usersList, uID, password);
    }

    private void myPlaylist() throws SQLException, InvalidException, UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        playlist = pld.readAllPlaylist(uID);
        plo.displayAllPlaylists(playlist);
        do {
            System.out.println("SELECT A PLAYLIST(0-Exit):");
            int playlistId = s.nextInt();
            s.nextLine();
            if (playlistId == 0)
                return;
            else {
                if (plo.validPlaylist(playlist, playlistId)) {
                    //display all records present in the playlist, first songs then podcasts
                    slist = ld.readSongsLog(playlistId);
                    plist = ld.readPodcastsLog(playlistId);
                    List<Record> recordList=lo.displayByPlaylist(slist, plist);
                    PlayerConsole.player(recordList);
                }
                else
                    throw new InvalidException("INVALID PLAYLIST ID.");
            }
        }while(true);
    }
    private void addPlaylistEntry() throws SQLException {
        System.out.println("Enter the New Playlist name:");
        String name = s.nextLine();
        playId = pld.createPlaylist(name, uID);
        int choice;
        do {
            System.out.println("1.ADD SONG\t2.ADD PODCAST\t0.Exit");
            choice = s.nextInt();
            s.nextLine();
            switch (choice) {
                case 1:
                    this.addSongEntry();
                    break;
                case 2:
                    this.addPodcastEntry();
                    break;
            }
        }while (choice!=0);
    }
    private void addInExistingPlaylistEntry() throws SQLException, InvalidException {
        playlist = pld.readAllPlaylist(uID);
        plo.displayAllPlaylists(playlist);
        System.out.println("Enter Playlist id:");
        playId = s.nextInt();
        s.nextLine();

        //validate the playId
        if(plo.validPlaylist(playlist,playId)) {
            int choice;
            do {
                System.out.println("1.ADD SONG\t2.ADD PODCAST\t0.Exit");
                choice = s.nextInt();
                s.nextLine();
                switch (choice) {
                    case 1:
                        this.addSongEntry();
                        break;
                    case 2:
                        this.addPodcastEntry();
                        break;
                }
            } while (choice != 0);
        }
        else
            throw new InvalidException("Invalid Playlist Id.");
    }
    private void addSongEntry() throws SQLException {
        int choice;
        do{
            System.out.println("1.SEARCH BY ARTIST\t2.SEARCH BY GENRE\t3.SEARCH BY ALBUM\t0.Exit");
            choice= s.nextInt();
            s.nextLine();
            switch (choice){
                case 1://SEARCH BY ARTIST
                    System.out.println("Artist Name?");
                    String artist = s.nextLine();
                    so.searchByArtis(songList, artist);
                    do{
                        System.out.println("Enter id(0=Exit):");
                        int songId = s.nextInt();
                        s.nextLine();
                        if(songId!=0)
                            ld.insertSongLog(playId,songId);
                        else
                            break;
                    }while(true);
                    break;
                case 2://SEARCH BY GENRE
                    System.out.println("Genre Name?");
                    String genre = s.nextLine();
                    so.searchByGenere(songList, genre);
                    do{
                        System.out.println("Enter id(0=Exit):");
                        int songId = s.nextInt();
                        s.nextLine();
                        if(songId!=0)
                            ld.insertSongLog(playId,songId);
                        else
                            break;
                    }while(true);
                    break;
                case 3://SEARCH BY ALBUM
                    System.out.println("Album Name?");
                    String album = s.nextLine();
                    so.searchByAlbum(songList, album);
                    do{
                        System.out.println("Enter id(0=Exit):");
                        int songId = s.nextInt();
                        s.nextLine();
                        if(songId!=0)
                            ld.insertSongLog(playId,songId);
                        else
                            break;
                    }while(true);
                    break;
            }
        }while(choice!=0);
    }
    private void addPodcastEntry() throws SQLException {
        int choice;
        do {
            System.out.println("1.SEARCH BY CELEBRITY\t2.SEARCH BY PUBLISHING DATE\t0.Exit");
            choice = s.nextInt();
            s.nextLine();
            switch (choice){
                case 1://SEARCH BY CELEBRITY
                    System.out.println("Celebrity Name?");
                    String celebrity = s.nextLine();
                    po.searchByCelebrity(podcastList, celebrity);
                    do{
                        System.out.println("Enter id(0=Exit):");
                        int podId = s.nextInt();
                        s.nextLine();
                        if(podId!=0)
                            ld.insertPodcastLog(playId,podId);
                        else
                            break;
                    }while(true);
                    break;
                case 2://SEARCH BY PUBLISHING DATE
                    System.out.println("Publishing Date?");
                    String date = s.nextLine();
                    po.searchByReleaseDate(podcastList, Date.valueOf(date));
                    do{
                        System.out.println("Enter id(0=Exit):");
                        int podId = s.nextInt();
                        s.nextLine();
                        if(podId!=0)
                            ld.insertPodcastLog(playId,podId);
                        else
                            break;
                    }while(true);
                    break;
            }
        }while (choice!=0);
    }
}
