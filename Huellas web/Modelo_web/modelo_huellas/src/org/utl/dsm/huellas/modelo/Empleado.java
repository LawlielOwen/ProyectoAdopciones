
package org.utl.dsm.huellas.modelo;
import java.time.LocalDate;
public class Empleado extends Persona {
    private int idEmpleado;
    private String direccion;
    private int CP;
    private String codigo;
    private String rol;
    private int estatus;
    public Empleado(int idEmpleado, String direccion, int CP, String codigo, String rol, int estatus, String nombre, String app, String apm, String fechaNacimiento, String correo, String contraseña, String foto, String telefono, String genero) {
        super(nombre, app, apm, fechaNacimiento, correo, contraseña, foto, telefono, genero);
        this.idEmpleado = idEmpleado;
        this.direccion = direccion;
        this.CP = CP;
        this.codigo = codigo;
        this.rol = rol;
        this.estatus = estatus;
    }
   
    public Empleado() {
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCP() {
        return CP;
    }

    public void setCP(int CP) {
        this.CP = CP;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
    
    
}
