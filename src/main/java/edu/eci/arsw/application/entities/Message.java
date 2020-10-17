package edu.eci.arsw.application.entities;

import java.sql.Date;

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
 * Mensaje
 */
@Entity
@Table ( name = "mensaje" )
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat", nullable = false)
    private Chat chat;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emisor", nullable = false)
    private User emisor;
    
    private String contenido;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public Message(){
    }
    
    public Message(int id, Chat chat, User emisor, String contenido, Date fecha) {
        this.id = id;
        this.chat = chat;
        this.emisor = emisor;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getEmisor() {
        return emisor;
    }

    public void setEmisor(User emisor) {
        this.emisor = emisor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Message [chat=" + chat + ", contenido=" + contenido + ", emisor=" + emisor + ", fecha=" + fecha
                + ", id=" + id + "]";
    }*/

    

    /*
    id int primary key,
	chat int,
	emisor BIGINT,
	contenido varchar(250),
	fecha TIMESTAMP,*/
}
