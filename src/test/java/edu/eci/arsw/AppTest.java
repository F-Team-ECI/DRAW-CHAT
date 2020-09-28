package edu.eci.arsw;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.impl.DrawChatServiceImpl;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */

//@RunWith(SpringRunner.class)
//@DataJpaTest
public class AppTest {
	
	@Autowired
	DrawChatServiceImpl dcsi;
	
	
    public void addUser() {
    	
    }
    
    public void getUsers() {
    	
    }
    
    public void getUser() {
    	
    }
    
    
}
