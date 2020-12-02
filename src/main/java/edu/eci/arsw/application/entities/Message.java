package edu.eci.arsw.application.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat")
    private Chat chat;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emisor", nullable = false)
    private User emisor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grupo")
    @JsonIgnore
    private Group grupo;
    
    private String contenido;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public Message(){
    }

    public Message(int id, Chat chat, Group grupo, User emisor, String contenido, Date fecha) {
        this.id = id;
        this.chat = chat;
        this.emisor = emisor;
        this.grupo = grupo;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    //Chat
    public Message(int id, Chat chat, User emisor, String contenido, Date fecha) {
        this.id = id;
        this.chat = chat;
        this.emisor = emisor;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    //Grupo
    public Message(int id, Group grupo, User emisor, String contenido, Date fecha) {
        this.id = id;
        this.emisor = emisor;
        this.grupo = grupo;
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

    public Group getGrupo() {
        return grupo;
    }

    public void setGrupo(Group grupo) {
        this.grupo = grupo;
    }

    /**
     * Verifica si el mensaje se puede borrar, solo puede 
     * borrar el mensaje si han pasado menos de 10 minutos.
     * @return boolean indicando si puede borrarlo, false si no puede
     */
    public boolean allowedToDelete() {
        boolean isAllowed=false;
        Date currentDate = new Date();
        long diff = currentDate.getTime() - fecha.getTime();
        long minutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
        if (minutes<10) {
            isAllowed=true;
        }
        return isAllowed;
    }

    /*
    public static void main(String[] args) throws ParseException{
        Message msg = new Message();
        SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = objSDF.parse("2020-12-10 00:00:00");
        Date d2 = objSDF.parse("2020-12-10 00:10:54");
        System.out.println("Date1:" + objSDF.format(d1));
        System.out.println("Date2:" + objSDF.format(d2));
        long diff = d2.getTime() - d1.getTime();
        long en = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
        System.out.println(en);
    }*/

    @Override
    public String toString() {
        return "Message [id=" + id
                    + ", emisor=" + emisor.getTelefono()
                    + ", contenido=" + contenido 
                    + ", fecha=" + fecha
                    + ", grupo=" + grupo
                    + ", chat=" + chat + "]";
    }
    /*
    id int primary key,
	chat int,
	emisor BIGINT,
	contenido varchar(250),
	fecha TIMESTAMP,*/
}
