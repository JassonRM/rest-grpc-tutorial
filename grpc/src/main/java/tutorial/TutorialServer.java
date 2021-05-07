package tutorial;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class TutorialServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        Server server = ServerBuilder.forPort(port)
                .addService(new CalculatorService())
                .build()
                .start();
        System.out.println("Server running on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    if (server != null) {
                        server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
            }
        });

        // Keep server running
        if (server != null) {
            server.awaitTermination();
        }
    }
}
