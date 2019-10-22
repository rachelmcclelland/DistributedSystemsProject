package ie.gmit.ds;

import com.google.protobuf.BoolValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordClient {

    private static final Logger logger =
            Logger.getLogger(PasswordClient.class.getName());
    private final ManagedChannel channel;
    // private final PasswordServiceGrpc.PasswordServiceStub asyncPasswordService;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;

    public PasswordClient(String host, int port) {
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void hashPasswword(HashRequest hr) {
        logger.info("Hashing Password " + hr);
        HashResponse hashResponse = null;

        try {
            // hashes the password
            hashResponse = syncPasswordService.hash(hr);
            logger.info(("Hashed Password: " + hashResponse.getHashedPassword()));
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }
    }

    public static void main(String[] args) throws Exception {
        PasswordClient pClient = new PasswordClient("10.12.7.50", 50551);

        int userID = 123;
        String password = "helloworld";

        HashRequest hashRequest = HashRequest.newBuilder()
                .setPassword(password)
                .setUserID(userID)
                .build();

        try{
            pClient.hashPasswword(hashRequest);
        }finally {
            // Don't stop process, keep alive to receive async response
            Thread.currentThread().join();
        }
    }
}// PasswordClient class
