package ie.gmit.ds;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordClient {

    private static final Logger logger =
            Logger.getLogger(PasswordClient.class.getName());
    private final ManagedChannel channel;
    // private final PasswordServiceGrpc.PasswordServiceStub asyncPasswordService;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;

    // Constructor
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

    public HashResponse hashPasswword(HashRequest hr) {
        // hash the password
        logger.info("Hashing Password " + hr);
        HashResponse hashResponse = null;

        try {
            // hashes the password
            hashResponse = syncPasswordService.hash(hr);
            logger.info(("Hashed Password: " + hashResponse.getHashedPassword()));
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return hashResponse;
        }

        return hashResponse;
    }

    public static void main(String[] args) throws Exception {
        PasswordClient pClient = new PasswordClient("localhost", 50551);
        ValidateRequest vr = null;
        Scanner console = new Scanner(System.in);

        int userID;
        String password;

        System.out.println("Enter the user ID: ");
        userID = console.nextInt();

        System.out.println("Enter password: ");
        password = console.next();

        HashRequest hashRequest = HashRequest.newBuilder()
                .setPassword(password)
                .setUserID(userID)
                .build();

        try {
            HashResponse response = pClient.hashPasswword(hashRequest);

            vr = ValidateRequest.newBuilder()
                    .setSalt(response.getSalt())
                    .setPassword(password)
                    .setHashedPassword(response.getHashedPasswordBytes())
                    .build();
        }
        finally {
            // Don't stop process, keep alive to receive async response
            Thread.currentThread().join();
        }
    }
}// PasswordClient class
