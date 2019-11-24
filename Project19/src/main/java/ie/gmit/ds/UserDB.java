package ie.gmit.ds;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDB {

    public final static HashMap<Integer, UserAccount> users = new HashMap<>();
    private static PasswordClient pClient = new PasswordClient("localhost", 50551);

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

        HashRequest hashRequest = HashRequest.newBuilder()
                .setUserID(id)
                .setPassword(user.getPassword())
                .build();
        try {
            StreamObserver<HashResponse>  responseObserver = new StreamObserver<HashResponse>() {
                UserAccount newUser;

                @Override
                public void onNext(HashResponse hashResponse) {

                    newUser = new UserAccount(user.getUserID(), user.getUserName(), user.getEmail(), user.getPassword(), hashResponse.getHashedPassword(), hashResponse.getSalt());

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

    public static boolean login(Integer id, UserAccount user){
        boolean validatePass = false;

        try {
            UserAccount user1 = getUser(id);
            ValidateRequest vr = ValidateRequest.newBuilder()
                    .setHashedPassword(user1.getHashedPassword())
                    .setPassword(user.getPassword())
                    .setSalt(user1.getSalt())
                    .build();

            validatePass = pClient.checkValidation(vr);

            return validatePass;
        } finally {

        }
    }

}
