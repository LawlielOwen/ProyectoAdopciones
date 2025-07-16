package org.utl.dsm.huellas.control;

import org.utl.dsm.huellas.modelo.Donaciones;
import org.utl.dsm.huellas.db.ConexionMySQL;

import java.sql.CallableStatement;
import java.sql.Connection;

public class ControllerDonaciones {

    public int generar(Donaciones obj) throws Exception {
        ConexionMySQL connMysql = new ConexionMySQL();
        Connection conn = connMysql.open();

       
        CallableStatement cstmt = conn.prepareCall("{call sp_InsertarDonacionRetornaId(?, ?, ?, ?, ?)}");

        
        cstmt.setString(1, obj.getNombreDonante());
        cstmt.setDouble(2, obj.getMontoDonacion());
        cstmt.setString(3, obj.getCentroDonacion());
        cstmt.setInt(4,obj.getIdCentro());

        // Parametro OUT
        cstmt.registerOutParameter(5, java.sql.Types.INTEGER);

        // Ejecutar
        cstmt.executeUpdate();

        // Obtener id generado
        int idDonacionGenerado = cstmt.getInt(5);
        obj.setIdDonacion(idDonacionGenerado);

        // Cerrar recursos
        cstmt.close();
        connMysql.close();

        return idDonacionGenerado;
    }

}