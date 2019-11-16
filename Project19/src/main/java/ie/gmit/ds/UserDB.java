package ie.gmit.ds;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    private static  HashMap<Integer, UserAccount> users = new HashMap<>();

    static{
        users.put(1, new UserAccount(1, "rachel", "rachelm123", "abcde"));
        users.put(2, new UserAccount(2, "John", "john567", "1234"));
        users.put(3, new UserAccount(3, "Mary", "marymmary", "ab1234"));
    }

    public static List<UserAccount> getUsers(){
        return new ArrayList<UserAccount>(users.values());
    }

    public static UserAccount getUser(Integer id){
        return users.get(id);
    }

    public static void updateUser(Integer id, UserAccount user){
        users.put(id, user);
    }

    public static void deleteUser(Integer id){
        users.remove(id);
    }
}
