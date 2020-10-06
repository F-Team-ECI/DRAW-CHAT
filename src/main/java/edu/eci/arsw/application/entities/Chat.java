package edu.eci.arsw.application.entities;

import javax.persistence.*;

/**
 * Entidad chat de la aplicacion
 */
@Entity
@Table ( name = "chat" )
public class Chat extends MessageCenter{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /*
	usuario1 BIGINT,
	usuario2 BIGINT,
	tipo varchar(250),
    //@OneToMany messages*/

}
