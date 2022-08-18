package dao;

import databaseConnection.Connector;
import entity.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDao {

    //method to read all the playlist that corresponds to a given user_id.
    public List<Playlist> readAllPlaylist(int userId) throws SQLException {
        List<Playlist> list = new ArrayList<>();
        Connection con= Connector.getConnection();
        String query="SELECT * FROM Playlist WHERE U_ID=?";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1,userId);
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            Playlist e=new Playlist(rs.getInt(1),rs.getString(2));
            list.add(e);
        }
        return list;
    }

    //method to insert new data to the playlist table
    public int createPlaylist(String name,int uID) throws SQLException {
        Connection con=Connector.getConnection();
        String query="INSERT INTO PLAYLIST(PLAYLIST_NAME,U_ID) VALUES (?,?)";
        PreparedStatement ps=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,name);
        ps.setInt(2,uID);
        ps.executeUpdate();
        ResultSet rs=ps.getGeneratedKeys();//return the last primary key id.
        int playId=0;
        while (rs.next()){
            playId=rs.getInt(1);
        }
        return playId;
    }
}
