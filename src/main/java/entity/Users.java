package entity;

public class Users {
    private int userId;
    private String userName;
    private String password;
    //for EXISTING users
    public Users(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
    //for NEW user: they won't have user_id
    public Users(String userName, String password) {
        this.userName = userName;
        System.out.println("<USERS CONSTRUCTOR> user name :"+this.userName);
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
