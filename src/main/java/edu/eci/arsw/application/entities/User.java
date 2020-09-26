package edu.eci.arsw.application.entities;

import java.sql.Timestamp;

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
	private Timestamp fechaRegistro;
	private Timestamp fechaConexion;
	private String estado;

    public User() {
    }

    public User(String telefono, String nombre, String apellido, String contraseña, Timestamp fechaRegistro,
        Timestamp fechaConexion, String estado) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.fechaRegistro = fechaRegistro;
        this.fechaConexion = fechaConexion;
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

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Timestamp getFechaConexion() {
        return fechaConexion;
    }

    public void setFechaConexion(Timestamp fechaConexion) {
        this.fechaConexion = fechaConexion;
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
                + fechaConexion + ", fechaRegistro=" + fechaRegistro + ", nombre=" + nombre + ", telefono=" + telefono
                + "]";
    }

    
}
