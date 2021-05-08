
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestClient {

    private static final String REST_URI = "http://localhost:8080/test/calculator/";
    private static final Client client = ClientBuilder.newClient();

    public static Response ping() {
        return client
                .target(REST_URI)
                .path("ping")
                .request("text/plain")
                .get();
    }

    public static Response file() {
        return client
                .target(REST_URI)
                .path("file")
                .request(MediaType.APPLICATION_OCTET_STREAM)
                .get();
    }

    public static CalculatorOperation getAddition(int operandA, int operandB) {
        return client
                .target(REST_URI)
                .path("add")
                .path(String.valueOf(operandA))
                .path(String.valueOf(operandB))
                .request(MediaType.APPLICATION_JSON)
                .get(CalculatorOperation.class);
    }


    public static CalculatorOperation postAddition(CalculatorOperation operation) {
        return client
                .target(REST_URI)
                .path("process")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(operation, MediaType.APPLICATION_JSON), CalculatorOperation.class);
    }

    public static long timeTest() {
        long start = System.currentTimeMillis();
        file();
        long finish = System.currentTimeMillis();
        return finish - start;
    }

    public static void main(String[] args) {
        Response response = ping();
        System.out.println(response.readEntity(String.class));

        CalculatorOperation operation = getAddition(4, 5);
        System.out.printf("%d+%d=%d\n", operation.operandA, operation.operandB, operation.result);

        operation = new CalculatorOperation(4, 4);
        operation.operation = "addition";
        operation = postAddition(operation);
        System.out.printf("%d+%d=%d\n", operation.operandA, operation.operandB, operation.result);

        System.out.printf("Time: %dms\n", timeTest());
    }
}