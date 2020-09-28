package edu.eci.arsw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import edu.eci.arsw.application.services.impl.DrawChatServiceImpl;


/**
 * Unit test for simple App.
 */
public class AppTest {
	
	@Autowired
	DrawChatServiceImpl dcsi;
	
	@Test
    public void addUser() {
    	
    }
    
    public void getUsers() {
    	
    }
    
    public void getUser() {
    	
    }
    
    
}
