package edu.eci.arsw.application.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entidad usuario de la aplicacion
 */
@Entity
@Table ( name = "usuario" )
public class User {
    @Id
    private long telefono;

	private String nombre;
	private String apellido;
    private String contraseña;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharegistro;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaconexion;

    private String estado;

    public User() {
    }

    public User(long telefono, String nombre, String apellido, String contraseña, Date fecharegistro, Date fechaconexion, String estado) {
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

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public Date getFechaconexion() {
        return fechaconexion;
    }

    public void setFechaconexion(Date fechaconexion) {
        this.fechaconexion = fechaconexion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * El numero de telefono debe ser de 10 digitos
     * @return true si es valido, false si no lo es
     */
    public boolean validTelefono() {
        return Long.toString(telefono).length() == 10;
    }

    /**
     * El nombre debe tener mas de 3 caracteres
     * @return true si es valido, false si no lo es
     */
    public boolean validName() {
        return nombre.length() >= 3;
    }

    /**
     * El apellido debe tener mas de 3 caracteres
     * @return true si es valido, false si no lo es
     */
    public boolean validApellido() {
        return apellido.length() >= 3;
    }

    /**
     * La contraseña debe tener mas de 3 caracteres
     * @return true si es valido, false si no lo es
     */
    public boolean validPass() {
        return contraseña.length() > 3;
    }

    @Override
    public String toString() {
        return "User [telefono=" + telefono +
                     ", nombre=" + nombre + 
                     ", apellido=" + apellido + 
                     ", contraseña=" + contraseña + 
                     ", fecharegistro=" + fecharegistro + 
                     ", fechaconexion=" + fechaconexion + 
                     ", estado=" + estado + "]";
    }
}
