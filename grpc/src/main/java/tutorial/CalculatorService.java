package tutorial;

public class CalculatorService extends CalculatorGrpc.CalculatorImplBase {
    @Override
    public void add(tutorial.AddRequest request,
            io.grpc.stub.StreamObserver<tutorial.AddReply> responseObserver) {
        AddReply reply = AddReply.newBuilder().setResult(
                request.getOperandA() + request.getOperandB()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}