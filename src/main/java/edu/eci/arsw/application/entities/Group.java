package edu.eci.arsw.application.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
            }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "gruposusuario", 
            joinColumns = @JoinColumn(name = "grupo"), 
            inverseJoinColumns = @JoinColumn(name = "usuario"))
    private Set<User> members;

    public Group() {
    }

    public Group(int id, String nombre, String lema, Date fechaCreacion, Set<User> members) {
        this.id = id;
        this.nombre = nombre;
        this.lema = lema;
        this.fechaCreacion = fechaCreacion;
        this.members = members;
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

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
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
