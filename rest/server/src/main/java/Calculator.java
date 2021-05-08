import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("/calculator")
public class Calculator {
    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok().entity("Service online").build();
    }

    @GET
    @Path("/add/{operandA}/{operandB}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response parameterAddition(@PathParam("operandA") int operandA, @PathParam("operandB") int operandB) {
        CalculatorOperation operation = new CalculatorOperation(operandA, operandB);
        operation.add();
        return Response.ok().entity(operation).build();
    }

    @POST
    @Path("/process/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processOperation(CalculatorOperation operation) {
        if (operation.operation.equals("addition")) {
            operation.add();
            return Response.ok().entity(operation).build();
        } else {
            return Response.serverError().entity("Operation not supported").build();
        }
    }

    @GET
    @Path("/file")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile() {
        File file = new File("~/Downloads/100MB.zip");// Initialize this to the File path you want to serve.
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"") //optional
                .build();
    }
}