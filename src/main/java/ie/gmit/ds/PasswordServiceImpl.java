package ie.gmit.ds;

import com.google.protobuf.BoolValue;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class PasswordServiceImpl extends PasswordServiceGrpc.PasswordServiceImplBase {

    private static final Logger logger =
            Logger.getLogger(PasswordServiceImpl.class.getName());

    private String password;
    private int userId;

    @Override
    public void hash(HashRequest request, StreamObserver<HashResponse> responseObserver) {
        // get info from the request
        // request password
         password = request.getPassword();
         logger.info("Password is: " + password);

         userId = request.getUserID();

        // use john frenches code for hash
        // build repsonse using builder


        // send response back after
    }

    @Override
    public void validate(ValidateRequest request, StreamObserver<BoolValue> responseObserver) {
        super.validate(request, responseObserver);
    }

}
