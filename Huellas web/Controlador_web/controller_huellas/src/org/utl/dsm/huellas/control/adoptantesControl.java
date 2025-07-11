package org.utl.dsm.huellas.control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import java.util.Base64;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.huellas.db.ConexionMySQL;
import org.utl.dsm.huellas.modelo.Adoptante;

public class adoptantesControl {

    public int insertarAdoptante(Adoptante ad) throws Exception {
        String sql = "{call  registrarAdoptante(?, ?, ?,?,?,?,?,?,?)}";
        int idGenerado = -1;

        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();
        CallableStatement cstmt = con.prepareCall(sql);

        cstmt.setString(1, ad.getNombre());
        cstmt.setString(2, ad.getApp());
        cstmt.setString(3, ad.getApm());
        cstmt.setString(4, ad.getFechaNacimiento());
        cstmt.setString(5, ad.getGenero());
        cstmt.setString(6, ad.getCorreo());
        cstmt.setString(7, ad.getContraseña());
        cstmt.setString(8, ad.getTelefono());
        cstmt.registerOutParameter(9, Types.INTEGER);

        cstmt.executeUpdate();
        idGenerado = cstmt.getInt(9);
       ad.setIdAdoptante(idGenerado);

        cstmt.close();
        conn.close();

        return idGenerado;
    }

public Adoptante logInUser(Adoptante e) throws Exception {
    String sql = "{call revisarAdoptante(?, ?, ?)}";
    int idEncontrado = 0;
    
    ConexionMySQL conn = new ConexionMySQL();
    Connection con = conn.open();
    CallableStatement cstmt = con.prepareCall(sql);
    
    cstmt.setString(1, e.getCorreo());
    cstmt.setString(2, e.getContraseña());
    cstmt.registerOutParameter(3, Types.INTEGER);
    cstmt.executeUpdate();
    idEncontrado = cstmt.getInt(3);
    
    Adoptante adoptante = null;
    if (idEncontrado != 0) {
        String sqlDatos = "SELECT * FROM verAdoptante WHERE id_adoptante = ?";
        PreparedStatement pstmt = con.prepareStatement(sqlDatos);
        pstmt.setInt(1, idEncontrado);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            adoptante = fill(rs);
        }
        
        rs.close();
        pstmt.close();
    }
  
    cstmt.close();
    conn.close();
    
    return adoptante;
}
    public List<Adoptante> getAll(String filtro) throws Exception {
        String sql = "select * from verAdoptante";
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        CallableStatement cstmt = conns.prepareCall(sql);
        ResultSet rs = cstmt.executeQuery();
        List<Adoptante> cliente = new ArrayList<>();
        while (rs.next()) {
            cliente.add(fill(rs));
        }
        rs.close();
        cstmt.close();
        conn.close();
        return cliente;
    }
private Adoptante fill(ResultSet rs) throws Exception {
    Adoptante a = new Adoptante();
    a.setIdAdoptante(rs.getInt("id_adoptante"));
    a.setNombre(rs.getString("nombre"));
    a.setApp(rs.getString("app"));
    a.setApm(rs.getString("apm"));
    a.setFechaNacimiento(rs.getString("fechaNacimiento"));
    a.setGenero(rs.getString("genero"));
    a.setCorreo(rs.getString("correo"));
    a.setContraseña(rs.getString("contraseña"));
    a.setTelefono(rs.getString("telefono"));
    a.setEstatus(rs.getInt("estatus"));
    return a;
}
}
