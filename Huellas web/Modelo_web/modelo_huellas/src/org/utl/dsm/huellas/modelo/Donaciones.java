package org.utl.dsm.huellas.modelo;

import java.time.LocalDate;

public class Donaciones {
    private int idDonacion;
    private String nombreDonante;
    private double montoDonacion;
    private LocalDate fechaDonacion;
    private String centroDonacion;
    private int idCentro;

    // Constructor
    public Donaciones() { }
    
    

    // Getters y Setters
    public int getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(int idDonacion) {
        this.idDonacion = idDonacion;
    }

    public String getNombreDonante() {
        return nombreDonante;
    }

    public void setNombreDonante(String nombreDonante) {
        this.nombreDonante = nombreDonante;
    }

    public double getMontoDonacion() {
        return montoDonacion;
    }

    public void setMontoDonacion(double montoDonacion) {
        this.montoDonacion = montoDonacion;
    }

    public LocalDate getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(LocalDate fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }

    public String getCentroDonacion() {
        return centroDonacion;
    }

    public void setCentroDonacion(String centroDonacion) {
        this.centroDonacion = centroDonacion;
    }

    public int getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
    }
}