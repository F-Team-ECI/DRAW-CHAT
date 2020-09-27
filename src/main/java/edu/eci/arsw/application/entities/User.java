package edu.eci.arsw.application.entities;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table ( name = "usuario" )
public class User {
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private int id;
    private long telefono;
	private String nombre;
	private String apellido;
    private String contraseña;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="America/Bogota")
    private Timestamp fecharegistro;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="America/Bogota")
    private Timestamp fechaconexion;

    private String estado;
    
    public User() {
    }

    public User(long telefono, String nombre, String apellido, String contraseña, Timestamp fecharegistro, Timestamp fechaconexion, String estado) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.fecharegistro = fecharegistro;
        this.fechaconexion = fechaconexion;
        this.estado = estado;
    }
    
    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
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
        return "User [apellido=" + apellido + ", contraseña=" + contraseña + ", estado=" + estado + ", fechaconexion="
                + fechaconexion + ", fecharegistro=" + fecharegistro + ", nombre=" + nombre + ", telefono=" + telefono
                + "]";
    }
}
