package databaseConnection;

import java.sql.*;

public class Connector {
    //singleton class for Connection con.
    private static Connection con=null;
    static{
        //load driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
        //establish Connection
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","Root@5222");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection Established");
    }
    public static Connection getConnection(){
        return con;
    }

    public static void closeConnection() throws SQLException {
        con.close();
        System.exit(0);//required to close the console and cmd
    }
}
