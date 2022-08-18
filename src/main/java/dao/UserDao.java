package dao;

import databaseConnection.Connector;
import entity.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    //method to read all the available users
    public List<Users> readAllUsers() throws SQLException {
        List<Users> list = new ArrayList<>();
        Connection con= Connector.getConnection();
        Statement stmt= con.createStatement();
        ResultSet rs= stmt.executeQuery("SELECT * FROM Users");
        while(rs.next()){
            Users e=new Users(rs.getInt(1),rs.getString(2),rs.getString(3));
            list.add(e);
        }
        return list;
    }

    //method to insert data of a new user in the user table
    public void newUser(Users u) throws SQLException {
        Connection con= Connector.getConnection();
        String query="INSERT INTO USERS(U_NAME,PASSWORD) VALUES(?,?)";
        PreparedStatement ps=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,u.getUserName());
        ps.setString(2,u.getPassword());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        int userId = 0;
        if (rs.next()) {
            userId = rs.getInt(1);
        }
        System.out.println("NEW USER CREATED :\nUSER NAME: "+u.getUserName()+"\nUSER ID: "+userId+"\nPASSWORD :"+u.getPassword());
    }
}
