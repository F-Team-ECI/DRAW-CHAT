package edu.eci.arsw.application.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * Grupo
 */
@Entity
@Table ( name = "grupo" )
public class Group extends MessageCenter{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nombre;
    private String lema;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public Group() {
    }

    public Group(int id, String nombre, String lema, Date fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.lema = lema;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLema() {
        return lema;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Group [id=" + id
                + ", nombre=" + nombre 
                + ", lema=" + lema 
                + ", messages=" + messages 
                + ", fechaCreacion=" + fechaCreacion + "]";
    }

    /*
    id int primary key,
	nombre varchar(250),
	lema varchar(250),
    fechaCreacion TIMESTAMP
    */
}
