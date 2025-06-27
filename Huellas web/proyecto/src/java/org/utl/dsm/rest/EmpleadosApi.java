
package org.utl.dsm.rest;
import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import jakarta.ws.rs.Path;
import org.utl.dsm.huellas.modelo.Empleado;
import org.utl.dsm.huellas.control.loginControl;
@Path("Empleados")
public class EmpleadosApi extends Application{
    @POST
    @Path("consultaEmpleado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checarEmpleado(@FormParam("datosUsuario")String datosUsuario)     
    {
        String out = null;
        Empleado e = null;
        Gson gson = new Gson();

        try {
            e = gson.fromJson(datosUsuario, Empleado.class);

            loginControl cu = new loginControl();
            int idUsuario = cu.logInEmpleado(e);

            if (idUsuario != 0) {
                out = gson.toJson(e);
            } else {
                out = "{\"mensaje\":\"No existe tu cuenta \"}";
            }
        } catch (Exception a) {
            out = "{\"error\":\"Error en el formato de los datos\"}";
            a.printStackTrace();
        }
         return Response.status(Response.Status.OK).entity(out).build();

    }
}
