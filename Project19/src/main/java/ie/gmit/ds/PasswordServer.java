package ie.gmit.ds;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;


public class PasswordServer {

    private Server grpcServer;
    private static final Logger logger = Logger.getLogger(PasswordServer.class.getName());
    private static final int PORT = 50551;

    private void start(int port) throws IOException {
        grpcServer = ServerBuilder.forPort(port)
                .addService(new PasswordServiceImpl())
                .build()
                .start();

        logger.info("Server started, listening on " + PORT);
    }

    private void stop() {
        if (grpcServer != null) {
            grpcServer.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (grpcServer != null) {
            grpcServer.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 0;
        Scanner console = new Scanner(System.in);
        boolean rightPort = false;

        final PasswordServer passwordServer = new PasswordServer();


        while(!rightPort)
        {
            System.out.println("Enter the port number: ");

            port = console.nextInt();

            if(port == 50551 ) {
                passwordServer.start(port);
                rightPort = true;
            }
            else{
                System.out.println("Wrong port entered, please try again");
                rightPort = false;
            }
        }



        passwordServer.blockUntilShutdown();
    }

}
