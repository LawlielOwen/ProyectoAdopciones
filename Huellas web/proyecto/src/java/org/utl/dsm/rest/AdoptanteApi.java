
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
import org.utl.dsm.huellas.modelo.Adoptante;
import org.utl.dsm.huellas.control.adoptantesControl;
@Path("adoptante")
public class AdoptanteApi extends Application {
     @POST
    @Path("consultaAd")
     @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checarAd(String datosUsuario)     
    {
        String out = null;
        Adoptante e = null;
        Gson gson = new Gson();

        try {
            e = gson.fromJson(datosUsuario, Adoptante.class);

            adoptantesControl cu = new adoptantesControl();
            Adoptante a = cu.logInUser(e);

            if (a != null) {
                out = gson.toJson(a);
            } else {
                out = "{\"mensaje\":\"No existe tu cuenta \"}";
            }
        } catch (Exception a) {
            out = "{\"error\":\"Error en el formato de los datos\"}";
            a.printStackTrace();
        }
         return Response.status(Response.Status.OK).entity(out).build();

    }
     @POST
    @Path("registroAd")
     @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checarEmpleado(String datosUsuario)     
    {
        String out = null;
        Adoptante e = null;
        Gson gson = new Gson();

        try {
            e = gson.fromJson(datosUsuario, Adoptante.class);

              adoptantesControl cu = new adoptantesControl();
            int idUsuario = cu.insertarAdoptante(e);

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
       @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        String out = null;
        List<Adoptante> lista;
       adoptantesControl a = new adoptantesControl();
        try {
            lista = a.getAll(out);
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener los adoptantes.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
