package edu.eci.arsw.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Core
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.application"})
@EntityScan("edu.eci.arsw.application.entities")
@EnableJpaRepositories(basePackageClasses = edu.eci.arsw.application.persistence.DAO.UserDAO.class)
public class DrawChatApp 
{
    public static void main( String[] args )
    {
        SpringApplication.run(DrawChatApp.class, args);
    }
}
