package dao;

import databaseConnection.Connector;
import entity.Log;
import entity.Podcasts;
import entity.Tracks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDao {
    Connection con= Connector.getConnection();

    //method to read all songs that correspond to the given playlist_id.
    public List<Log> readSongsLog(int playId) throws SQLException {

        List<Log> list=new ArrayList<>();
        String query="SELECT L.LOG_ID,L.T_ID,T.SONG,T.ARTIST,T.GENRE,T.ALBUM,T.DURATION,T.LOCATION FROM LOG L JOIN TRACKS T WHERE  L.PLAYLIST_ID=? AND L.T_ID!='NULL' AND L.T_ID=T.T_ID";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1,playId);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            int logId=rs.getInt(1);
            int tId=rs.getInt(2);
            String song=rs.getString(3);
            String artist=rs.getString(4);
            String genre=rs.getString(5);
            String album=rs.getString(6);
            Time time=rs.getTime(7);
            String location=rs.getString(8);
            Log l=new Log(logId,new Tracks(tId,song,artist,genre,album,time,location));
            list.add(l);
        }
        return list;
    }

    //method to read all podcasts corresponding to the given playlist_id
    public List<Log> readPodcastsLog(int playId) throws SQLException {
        List<Log> list=new ArrayList<>();
        String query="SELECT L.LOG_ID,L.P_ID,P.PODCAST,P.CELEBRITY,P.RELEASE_DATE,P.DURATION,P.LOCATION FROM LOG L JOIN PODCASTS P WHERE  L.PLAYLIST_ID=? AND L.P_ID!='NULL' AND L.P_ID=P.P_ID;";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1,playId);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            int logId=rs.getInt(1);
            int pId=rs.getInt(2);
            String podcast=rs.getString(3);
            String celebrity=rs.getString(4);
            Date release =rs.getDate(5);
            Time time=rs.getTime(6);
            String location=rs.getString(7);
            Log l=new Log(logId,new Podcasts(pId,podcast,celebrity,release,time,location));
            list.add(l);
        }
        return list;
    }

    //method to insert new song data to the log table
    public void insertSongLog(int playId,int tID) throws SQLException {
        String query="INSERT INTO LOG(PLAYLIST_ID,T_ID) VALUES(?,?)";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1,playId);
        ps.setInt(2,tID);
        int count=ps.executeUpdate();
        System.out.println(count+"record(s) updated");
    }

    //method to insert new podcast data to the log table.
    public void insertPodcastLog(int playId,int pID) throws SQLException {
        String query="INSERT INTO LOG(PLAYLIST_ID,P_ID) VALUES(?,?)";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1,playId);
        ps.setInt(2,pID);
        int count=ps.executeUpdate();
        System.out.println(count+"record(s) updated");
    }
}