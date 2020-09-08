package edu.eci.arsw.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Core
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.application"})
public class DrawChatApp 
{
    public static void main( String[] args )
    {
        SpringApplication.run(DrawChatApp.class, args);
    }
}
