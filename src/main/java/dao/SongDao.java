package dao;

import databaseConnection.Connector;
import entity.Tracks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongDao {

    //method to read all the available songs
    public List<Tracks> readAllSongs() throws SQLException {
        List<Tracks> songList = new ArrayList<>();
        Connection con=Connector.getConnection();
        Statement stmt= con.createStatement();
        ResultSet rs= stmt.executeQuery("SELECT * FROM TRACKS");
        while(rs.next()){
            Tracks e=new Tracks(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getTime(6),rs.getString(7));
            //assert false;
            songList.add(e);
        }
        return songList;
    }
}
