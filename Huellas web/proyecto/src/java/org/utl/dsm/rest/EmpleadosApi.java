package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.utl.dsm.huellas.control.ControllerEmpleados;
import org.utl.dsm.huellas.modelo.Empleado;

@Path("Empleados")
public class EmpleadosApi extends Application {

    private final Gson gson = new Gson();

    // Insertar empleado (JSON)
    @POST
    @Path("insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarEmpleado(String datosEmpleado) {
        try {
            Empleado e = gson.fromJson(datosEmpleado, Empleado.class);
            ControllerEmpleados ce = new ControllerEmpleados();
            ce.insertarEmpleado(e);
            return Response.ok(gson.toJson(e)).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Error al insertar el empleado\"}").build();
        }
    }

    // Listar empleados (con parámetro estatus opcional)
    @GET
    @Path("listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEmpleados(@QueryParam("estatus") @DefaultValue("-1") int estatus) {
        try {
            ControllerEmpleados ce = new ControllerEmpleados();
            return Response.ok(gson.toJson(ce.listarEmpleados(estatus))).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Error al listar empleados\"}").build();
        }
    }

    // Actualizar empleado (JSON)
    @PUT
    @Path("modificar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarEmpleado(String datosEmpleado) {
        try {
            Empleado e = gson.fromJson(datosEmpleado, Empleado.class);
            ControllerEmpleados ce = new ControllerEmpleados();
            ce.actualizarEmpleado(e);
            return Response.ok("{\"mensaje\":\"Empleado actualizado correctamente\"}").build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Error al actualizar el empleado\"}").build();
        }
    }

    // Eliminar empleado lógico por idPersona
    @DELETE
    @Path("eliminar/{idPersona}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarEmpleado(@PathParam("idPersona") int idPersona) {
        try {
            ControllerEmpleados ce = new ControllerEmpleados();
            ce.eliminarEmpleadoLogico(idPersona);
            return Response.ok("{\"mensaje\":\"Empleado eliminado lógicamente\"}").build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Error al eliminar el empleado\"}").build();
        }
    }

    // Buscar empleados (filtro)
    @GET
    @Path("buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEmpleado(@QueryParam("filtro") String filtro) {
        try {
            ControllerEmpleados ce = new ControllerEmpleados();
            return Response.ok(gson.toJson(ce.buscarEmpleados(filtro))).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Error en la búsqueda\"}").build();
        }
    }
}