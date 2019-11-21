package ie.gmit.ds;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordClient {
    private static final Logger logger =
            Logger.getLogger(PasswordClient.class.getName());
    private final ManagedChannel channel;
    private final PasswordServiceGrpc.PasswordServiceStub asyncPasswordService;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPassowrdService;

    //Connect to the server with a socket(IP and port number).
    public PasswordClient(String host, int port) {
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        asyncPasswordService = PasswordServiceGrpc.newStub(channel);
        syncPassowrdService = PasswordServiceGrpc.newBlockingStub(channel);
    }
    //Shutdown the server if interupted.
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    //Make asychronous hash request call to the server
    public void hashUserpwd(HashRequest newHashRequest, StreamObserver<HashResponse> callback) {
        try {
            asyncPasswordService.hash(newHashRequest, callback);
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
        }
    }

    //Make a sychronous validation request to the server and return a boolean response
    public boolean checkValidation(ValidateRequest requestValidate) {
        boolean validationResponse = false;
        try {
            validationResponse = syncPassowrdService.validate(requestValidate).getValue();
            return validationResponse;
        } catch (
                StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
        }
        return validationResponse;
    }

}
