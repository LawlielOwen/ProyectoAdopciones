
package org.utl.dsm.rest;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("prueba")
public class PruebaApi extends Application{
        @Path("saludar")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response saludar()
    {
        String out =  """
                      {"result":"Hola Mundo"}
                      """;
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
