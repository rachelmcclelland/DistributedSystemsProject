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
            hashedpassword  = Passwords.hash(passwordChar, salt);

            //logger.info("Password is: " + password);

            //Build object
            HashResponse hr = HashResponse.newBuilder()
                    .setSalt(ByteString.copyFrom(salt))
                    .setHashedPassword(ByteString.copyFrom(hashedpassword))
                    .setUserID(userId)
                    .build();

            validatePass = Passwords.isExpectedPassword(passwordChar, salt, hashedpassword);
            System.out.println("User ID: " + userId + "\nPassword: " + password + "\nHashed Password: " + hashedpassword);

            if(validatePass)
            {
                System.out.println("Password and Hashed Password match");
            }
            else
            {
                System.out.println("Password and Hashed Password do not match");
            }

            responseObserver.onNext(hr);
        } catch (RuntimeException ex) {
            responseObserver.onNext(null);
        }
        responseObserver.onCompleted();

    }

    @Override
    public void validate(ValidateRequest request, StreamObserver<BoolValue> responseObserver) {
        try {

            String pwd = request.getPassword();
            char[] pwdCharArray = pwd.toCharArray();
            byte[] salt = Passwords.getNextSalt();
            byte[] hashedPassword  = Passwords.hash(pwdCharArray,salt);

            logger.info("Checking the validity of password " );
            boolean validationRequest = Passwords.isExpectedPassword(pwdCharArray,salt,hashedPassword );
            if(validationRequest==true){
                responseObserver.onNext(BoolValue.newBuilder().setValue(true).build());
            }else{
                responseObserver.onNext(BoolValue.newBuilder().setValue(false).build());
            }
        } catch (RuntimeException ex) {
            responseObserver.onNext(BoolValue.newBuilder().setValue(false).build());
        }
        responseObserver.onCompleted();
    }

}