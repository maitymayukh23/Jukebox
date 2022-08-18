package operation;

import entity.Users;

import java.util.List;
import java.util.stream.Collectors;

public class UserOperation {

    //method to validate a user by their user-id and password
    public boolean validUser(List<Users> userList, int uID, String password){
        List<Users> res=userList.stream().filter(s->s.getUserId()==uID && s.getPassword().equals(password)).collect(Collectors.toList());
        return res.size() == 1;
    }
}
