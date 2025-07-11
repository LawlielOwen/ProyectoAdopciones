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
import org.utl.dsm.huellas.modelo.Centros;
public class centroControl {
    private Centros  fill(ResultSet rs) throws Exception {
        Centros c = new Centros();
        c.setIdCentro(rs.getInt("id_centro"));
        c.setNombreCentro(rs.getString("nombre_centro"));
        c.setDireccionCentro(rs.getString("direccion_centro"));
        c.setTelefonoCentro(rs.getString("telefono_centro"));
        c.setCorreoCentro(rs.getString("correo_centro"));
        c.setHorarioAperturaCentro(rs.getString("horarioApertura_centro"));
        c.setHorarioCierreCentro(rs.getString("horarioCierre_centro"));
        c.setEstatus(rs.getInt("estatus_centro"));
        return c;
    }
     public List<Centros> getAll(String filtro) throws Exception {
        String sql = "select * from tbl_centros";
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        CallableStatement cstmt = conns.prepareCall(sql);
        ResultSet rs = cstmt.executeQuery();
        List<Centros> centro = new ArrayList<>();
        while (rs.next()) {
            centro.add(fill(rs));
        }
        rs.close();
        cstmt.close();
        conn.close();
        return centro;
    }
}
