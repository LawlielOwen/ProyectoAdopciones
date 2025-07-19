package org.utl.dsm.huellas.control;

import java.sql.*;
import java.util.*;
import org.utl.dsm.huellas.db.ConexionMySQL;
import org.utl.dsm.huellas.modelo.Empleado;

public class ControllerEmpleados {

    public void insertarEmpleado(Empleado e) throws Exception {
        // Validar campos mínimos antes de insertar
        validarEmpleado(e);

        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();
        try {
            con.setAutoCommit(false);

            // Insertar persona
            String queryPersona = "INSERT INTO tbl_personas(nombre_persona, app_persona, apm_persona, nacimiento_persona, genero_persona, correo_persona, contraseña_persona, foto_persona, telefono_persona, estatus_persona) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";
            PreparedStatement psPersona = con.prepareStatement(queryPersona, Statement.RETURN_GENERATED_KEYS);
            psPersona.setString(1, e.getNombre());
            psPersona.setString(2, e.getApp());
            psPersona.setString(3, e.getApm());
            psPersona.setString(4, e.getFechaNacimiento());  // Formato 'yyyy-MM-dd' en String
            psPersona.setString(5, e.getGenero());
            psPersona.setString(6, e.getCorreo());
            psPersona.setString(7, e.getContraseña());
            psPersona.setString(8, e.getFoto());
            psPersona.setString(9, e.getTelefono());

            int affectedRows = psPersona.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo insertar la persona");
            }

            ResultSet rsPersona = psPersona.getGeneratedKeys();
            if (rsPersona.next()) {
                int idPersona = rsPersona.getInt(1);
                e.setIdPersona(idPersona);

                // Generar código empleado
                String codigo = generarCodigoEmpleado(e, con);
                e.setCodigo(codigo);

                // Insertar empleado
                String queryEmpleado = "INSERT INTO tbl_empleados(direccion_empleado, codigoPostal_empleado, codigo_empleado, rol_empleado, id_centro, id_persona) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement psEmpleado = con.prepareStatement(queryEmpleado, Statement.RETURN_GENERATED_KEYS);
                psEmpleado.setString(1, e.getDireccion());
                psEmpleado.setInt(2, e.getCP());
                psEmpleado.setString(3, codigo);
                psEmpleado.setString(4, e.getRol());
                psEmpleado.setInt(5, e.getCentro());
                psEmpleado.setInt(6, idPersona);

                psEmpleado.executeUpdate();

                ResultSet rsEmpleado = psEmpleado.getGeneratedKeys();
                if (rsEmpleado.next()) {
                    e.setIdEmpleado(rsEmpleado.getInt(1));
                }

                psEmpleado.close();
                rsEmpleado.close();
            } else {
                throw new SQLException("No se pudo obtener el ID de la persona");
            }

            rsPersona.close();
            psPersona.close();

            con.commit();
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }

    private String generarCodigoEmpleado(Empleado e, Connection con) throws Exception {
        // Validar y asignar valores por defecto si campos son null o muy cortos
        String nombre = (e.getNombre() != null && e.getNombre().length() >= 1)
                ? e.getNombre().substring(0, 1).toUpperCase()
                : "X";
        String app = (e.getApp() != null && e.getApp().length() >= 2)
                ? e.getApp().substring(0, 2).toUpperCase()
                : "XX";
        String apm = (e.getApm() != null && e.getApm().length() >= 1)
                ? e.getApm().substring(0, 1).toUpperCase()
                : "X";
        String anio = "00";
        if (e.getFechaNacimiento() != null && e.getFechaNacimiento().length() >= 4) {
            anio = e.getFechaNacimiento().substring(2, 4);
        }

        String query = "SELECT COUNT(*) AS total FROM tbl_empleados";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        int consecutivo = 1;
        if (rs.next()) {
            consecutivo = rs.getInt("total") + 1;
        }
        rs.close();
        st.close();

        return (nombre + app + apm + anio + consecutivo).toUpperCase();
    }

    public List<Empleado> listarEmpleados(int estatus) throws Exception {
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();
        List<Empleado> lista = new ArrayList<>();
        try {
            String query = "SELECT p., e. FROM tbl_empleados e "
                    + "JOIN tbl_personas p ON e.id_persona = p.id_persona "
                    + (estatus != -1 ? "WHERE p.estatus_persona = ?" : "");

            PreparedStatement ps = con.prepareStatement(query);
            if (estatus != -1) {
                ps.setInt(1, estatus);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("id_empleado"));
                e.setIdPersona(rs.getInt("id_persona"));
                e.setNombre(rs.getString("nombre_persona"));
                e.setApp(rs.getString("app_persona"));
                e.setApm(rs.getString("apm_persona"));
                e.setFechaNacimiento(rs.getString("nacimiento_persona"));
                e.setGenero(rs.getString("genero_persona"));
                e.setCorreo(rs.getString("correo_persona"));
                e.setContraseña(rs.getString("contraseña_persona"));
                e.setFoto(rs.getString("foto_persona"));
                e.setTelefono(rs.getString("telefono_persona"));
                e.setDireccion(rs.getString("direccion_empleado"));
                e.setCP(rs.getInt("codigoPostal_empleado"));
                e.setCodigo(rs.getString("codigo_empleado"));
                e.setRol(rs.getString("rol_empleado"));
                e.setCentro(rs.getInt("id_centro"));
                e.setEstatus(rs.getInt("estatus_persona"));

                lista.add(e);
            }

            rs.close();
            ps.close();
        } finally {
            con.close();
        }

        return lista;
    }

    public void actualizarEmpleado(Empleado e) throws Exception {
        // Validar campos mínimos antes de actualizar
        validarEmpleado(e);

        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();

        try {
            con.setAutoCommit(false);

            String queryPersona = "UPDATE tbl_personas SET nombre_persona=?, app_persona=?, apm_persona=?, nacimiento_persona=?, genero_persona=?, correo_persona=?, contraseña_persona=?, foto_persona=?, telefono_persona=? WHERE id_persona=?";
            PreparedStatement psPersona = con.prepareStatement(queryPersona);
            psPersona.setString(1, e.getNombre());
            psPersona.setString(2, e.getApp());
            psPersona.setString(3, e.getApm());
            psPersona.setString(4, e.getFechaNacimiento());
            psPersona.setString(5, e.getGenero());
            psPersona.setString(6, e.getCorreo());
            psPersona.setString(7, e.getContraseña());
            psPersona.setString(8, e.getFoto());
            psPersona.setString(9, e.getTelefono());
            psPersona.setInt(10, e.getIdPersona());
            psPersona.executeUpdate();
            psPersona.close();

            String queryEmpleado = "UPDATE tbl_empleados SET direccion_empleado=?, codigoPostal_empleado=?, rol_empleado=?, id_centro=? WHERE id_persona=?";
            PreparedStatement psEmpleado = con.prepareStatement(queryEmpleado);
            psEmpleado.setString(1, e.getDireccion());
            psEmpleado.setInt(2, e.getCP());
            psEmpleado.setString(3, e.getRol());
            psEmpleado.setInt(4, e.getCentro());
            psEmpleado.setInt(5, e.getIdPersona());
            psEmpleado.executeUpdate();
            psEmpleado.close();

            con.commit();
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }

    public void eliminarEmpleadoLogico(int idPersona) throws Exception {
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();

        String query = "UPDATE tbl_personas SET estatus_persona = 0 WHERE id_persona=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, idPersona);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    public List<Empleado> buscarEmpleados(String filtro) throws Exception {
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();
        List<Empleado> lista = new ArrayList<>();

        try {
            String query = "SELECT p., e. FROM tbl_empleados e "
                    + "JOIN tbl_personas p ON e.id_persona = p.id_persona "
                    + "WHERE CONCAT(p.nombre_persona, ' ', p.app_persona, ' ', p.apm_persona, ' ', e.codigo_empleado, ' ', e.rol_empleado) LIKE ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + filtro + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("id_empleado"));
                e.setIdPersona(rs.getInt("id_persona"));
                e.setNombre(rs.getString("nombre_persona"));
                e.setApp(rs.getString("app_persona"));
                e.setApm(rs.getString("apm_persona"));
                e.setFechaNacimiento(rs.getString("nacimiento_persona"));
                e.setGenero(rs.getString("genero_persona"));
                e.setCorreo(rs.getString("correo_persona"));
                e.setContraseña(rs.getString("contraseña_persona"));
                e.setFoto(rs.getString("foto_persona"));
                e.setTelefono(rs.getString("telefono_persona"));
                e.setDireccion(rs.getString("direccion_empleado"));
                e.setCP(rs.getInt("codigoPostal_empleado"));
                e.setCodigo(rs.getString("codigo_empleado"));
                e.setRol(rs.getString("rol_empleado"));
                e.setCentro(rs.getInt("id_centro"));
                e.setEstatus(rs.getInt("estatus_persona"));

                lista.add(e);
            }

            rs.close();
            ps.close();
        } finally {
            con.close();
        }

        return lista;
    }

    // Método para validar campos mínimos y evitar nulls inesperados
    private void validarEmpleado(Empleado e) throws Exception {
        if (e == null) {
            throw new Exception("Empleado no puede ser null");
        }
        if (e.getNombre() == null || e.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
        if (e.getApp() == null || e.getApp().trim().isEmpty()) {
            throw new Exception("El apellido paterno no puede estar vacío");
        }
        if (e.getApm() == null || e.getApm().trim().isEmpty()) {
            throw new Exception("El apellido materno no puede estar vacío");
        }
        if (e.getFechaNacimiento() == null || e.getFechaNacimiento().trim().isEmpty()) {
            throw new Exception("La fecha de nacimiento no puede estar vacía");
        }
        
    }
}


