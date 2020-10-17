package edu.eci.arsw.application.entities;

import javax.persistence.*;

/**
 * Entidad chat de la aplicacion
 */
@Entity
@Table ( name = "chat" )
public class Chat extends MessageCenter{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario1", nullable = false)
    private User user1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario2", nullable = false)
    private User user2;

	private String tipo;

    public Chat (){
    }

    public Chat(int id, User user1, User user2, String tipo) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.tipo = tipo;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Chat [id=" + id + ", tipo=" + tipo + ", user1=" + user1 + ", user2=" + user2 + "]";
    }

    /*
	usuario1 BIGINT,
	usuario2 BIGINT,
	tipo varchar(250),
    //@OneToMany messages*/

}
