package dao;

import databaseConnection.Connector;
import entity.Podcasts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PodcastDao {

    //method to read all the available podcasts
    public List<Podcasts> readAllPodcasts() throws SQLException {
        List<Podcasts> list = new ArrayList<>();
        Connection con= Connector.getConnection();
        Statement stmt= con.createStatement();
        ResultSet rs= stmt.executeQuery("SELECT * FROM Podcasts");
        while(rs.next()){
            Podcasts e=new Podcasts(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getTime(5),rs.getString(6));
            list.add(e);
        }
        return list;
    }
}
