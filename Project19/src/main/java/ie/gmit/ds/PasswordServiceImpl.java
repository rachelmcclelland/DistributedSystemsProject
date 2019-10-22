package ie.gmit.ds;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class PasswordServiceImpl extends PasswordServiceGrpc.PasswordServiceImplBase {

    private static final Logger logger =
            Logger.getLogger(PasswordServiceImpl.class.getName());

    private String password;
    private char[] passwordChar;
    private int userId;
    private byte[] salt;
    private byte[] hashedpassword;
    private boolean validatePass;

    @Override
    public void hash(HashRequest request, StreamObserver<HashResponse> responseObserver) {
        // get info from the request

        try {
            // request password
            password = request.getPassword();

            passwordChar = password.toCharArray();
            // gets user id
            userId = request.getUserID();

            //gets the salt
            salt = Passwords.getNextSalt();

            // hash the password
            hashedpassword = Passwords.hash(passwordChar, salt);

            //logger.info("Password is: " + password);

            HashResponse hr = HashResponse.newBuilder()
                    .setSalt(ByteString.copyFrom(salt))
                    .setHashedPassword(String.valueOf(hashedpassword))
                    .setUserID(userId)
                    .build();

            validatePass = Passwords.isExpectedPassword(passwordChar, salt, hashedpassword);
            System.out.println("User ID: " + userId + "Password: " + password + "Hashed Password: " + hashedpassword);
            System.out.println(validatePass);

        }
        catch (RuntimeException e)
        {
            Logger.getLogger("error");
        }





        // use john frenches code for hash
        // build repsonse using builder


        // send response back after
    }

    @Override
    public void validate(ValidateRequest request, StreamObserver<BoolValue> responseObserver) {
        super.validate(request, responseObserver);
    }

}
