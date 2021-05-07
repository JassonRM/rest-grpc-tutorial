package tutorial;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;

public class TutorialClient {
    public static void main(String[] args) throws Exception {
        String target = "localhost:50051";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext() // To avoid needing SSL certificates for the tutorial
                .build();
        CalculatorGrpc.CalculatorBlockingStub blockingStub = CalculatorGrpc.newBlockingStub(channel);

        int operandA = 4;
        int operandB = 7;

        AddRequest request = AddRequest.newBuilder().setOperandA(4).setOperandB(7).build();
        AddReply reply;
        try {
            reply = blockingStub.add(request);
            System.out.println(operandA + " + " + operandB + " = " + reply.getResult());
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
