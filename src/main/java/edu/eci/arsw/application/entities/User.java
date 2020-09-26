package edu.eci.arsw.application.entities;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table ( name = "usuario" )
public class User {
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private String telefono;
	private String nombre;
	private String apellido;
    private String contraseña;
    private Timestamp fecharegistro;
    private Timestamp fechaconexion;
    private String estado;
    
    public User() {
    }

    public User(String telefono, String nombre, String apellido, String contraseña, Timestamp fecharegistro, Timestamp fechaconexion, String estado) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.fecharegistro = fecharegistro;
        this.fechaconexion = fechaconexion;
        this.estado = estado;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Timestamp fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public Timestamp getFechaconexion() {
        return fechaconexion;
    }

    public void setFechaconexion(Timestamp fechaconexion) {
        this.fechaconexion = fechaconexion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "User [apellido=" + apellido + ", contraseña=" + contraseña + ", estado=" + estado + ", fechaConexion="
                + fechaconexion + ", fechaRegistro=" + fecharegistro + ", nombre=" + nombre + ", telefono=" + telefono
                + "]";
    }

    
}
