package ie.gmit.ds;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDB {

    public final static HashMap<Integer, UserAccount> users = new HashMap<>();

    static{
        users.put(1, new UserAccount(1, "rachel", "rachelm123" ));
        users.put(2, new UserAccount(2, "John", "john567"));
        users.put(3, new UserAccount(3, "Mary", "marymmary"));
    }

    public static List<UserAccount> getUsers(){
        return new ArrayList<UserAccount>(users.values());
    }

    public static UserAccount getUser(Integer id){
        return users.get(id);
    }

    public static void createUser(final Integer id, final UserAccount user){
        PasswordClient pClient = new PasswordClient("localhost", 50551);

        HashRequest hashRequest = HashRequest.newBuilder()
                .setUserID(id)
                .setPassword(user.getPassword())
                .build();
        try {
            StreamObserver<HashResponse>  responseObserver = new StreamObserver<HashResponse>() {
                UserAccount newUser;

                @Override
                public void onNext(HashResponse value) {
                    newUser = new UserAccount(user.getUserID(), user.getUserName(), user.getEmail(), value.getHashedPassword().toString(), value.getSalt().toString());
                }

                @Override
                public void onError(Throwable throwable) {

                }

                //save the user to the database
                @Override
                public void onCompleted() {
                    users.put(id, newUser);
                }
            };

            pClient.hashUserpwd(hashRequest, responseObserver);
        } finally {

        }

    }

    public static void updateUser(Integer id, UserAccount user){
        users.put(id, user);
    }

    public static void deleteUser(Integer id){
        users.remove(id);
    }

    //login
    /*public static void login(Integer id, UserAccount user){
        PasswordClient client = new PasswordClient("localhost", 50551);
        boolean isValid = false;
        try {
            //Get user from the database by user ID.
            User u = getUser(id);
            //Build a validate request to send a validation request
            RequestValidate requestValidate = RequestValidate.newBuilder()
                    .setHashedPassword(ByteString.copyFromUtf8(u.getHashPwd()))
                    .setPassword(u.getHashPwd())
                    .setSalt(ByteString.copyFromUtf8(u.getSalt()))
                    .build();

            //Send a validation request and get response which is a boolean value(Another way of doing it)
            //isValid = client.syncPassowrdService.validate(requestValidate).getValue();

            //Use method to validate request and get a bool value
            isValid = client.checkValidation(requestValidate);

            System.out.println("Is Valid "+ isValid);
        } finally {

        }
    }*/

}
