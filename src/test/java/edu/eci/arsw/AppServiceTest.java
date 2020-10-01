package edu.eci.arsw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import edu.eci.arsw.application.DrawChatApp;
import edu.eci.arsw.application.controllers.UserController;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;
import edu.eci.arsw.application.services.impl.DrawChatServiceImpl;


/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DrawChatApp.class)
@ActiveProfiles("test")
public class AppServiceTest {
	
	@Autowired
	DrawChatService service;

	@Test
	public void addUserWithIncorrectNameLength() {
		User user = new User();
		user.setTelefono(1111111115);
		user.setNombre("p");
		user.setApellido("prueba5");
		user.setContraseña("lxcmzxmz");
		
		try {
			service.addUser(user);
		} catch (AppException e) {
			assertTrue(e.getMessage().equals("Incorrect credentials"));
		}
		
	}
	
	@Test
	public void addUserWithIncorrectLastNameLength() {
		User user = new User();
		user.setTelefono(1111111115);
		user.setNombre("psddsdssd");
		user.setApellido("p");
		user.setContraseña("lxcmzxmz");
		
		try {
			service.addUser(user);
		} catch (AppException e) {
			assertTrue(e.getMessage().equals("Incorrect credentials"));
		}
		
	}

	@Test
    public void addUserWithCorrectCredential() {
		User user = new User();
		user.setTelefono(1111111111);
		user.setNombre("prueba");
		user.setApellido("prueba");
		user.setContraseña("sadasdasdas");
		boolean temp = false;
		try {
			service.addUser(user);
			for(User u: service.getUsers()) {
				System.out.println(u.getTelefono());
				if(u.getTelefono() == user.getTelefono()) temp = true;
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service.deleteUser(1111111111);
		assertTrue(temp);
		
    }
	
	@Test(expected = AppException.class)
    public void NotaddUserIncorrectCredential() throws AppException {
		User user = new User();
		user.setTelefono(111111111);
		user.setNombre("prueba");
		user.setApellido("prueba");
		user.setContraseña("sadasdasdas");
		boolean temp = false;
		service.addUser(user);
		
		
    }
    
	@Test
    public void getUsers() {
    	try {
    		
			service.getUsers();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	@Test
	public void addContact() {
		BigDecimal bd0 = new BigDecimal("3185560092");
		BigDecimal bd1 = new BigDecimal("3185560091");

		
		try {
			User u = service.getUser(bd1.longValue());
			service.addContact(bd1.longValue(),bd0.longValue());
			assertTrue(service.getContacts(bd0.longValue()).contains(u));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
    public void deleteUserAfterInsert() {
		User user = new User();
		user.setTelefono(1111111111);
		user.setNombre("prueba");
		user.setApellido("prueba");
		user.setContraseña("sadasdasdas");
		
		try {
			service.addUser(user);
			service.deleteUser(1111111111);
			assertTrue(!service.getUsers().contains(user));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
    }
	
	@Test(expected = AppException.class)
    public void AddUserWithoutNombre() throws AppException {
		User user = new User();
		user.setTelefono(1111111111);
		user.setApellido("prueba");
		user.setContraseña("sadasdasdas");
		
		service.addUser(user);
		
    }
	
	@Test(expected = NullPointerException.class)
    public void getCurrentUserSessionWouldBeNull() throws AppException {
    	service.getCurrentUserSession();
    }
	
	@Test
    public void AddUserAddContact() {
		User user = new User();
		user.setTelefono(1111111112);
		user.setNombre("prueba2");
		user.setApellido("prueba2");
		user.setContraseña("csacsacascaca");
		
		BigDecimal bd1 = new BigDecimal("3185560091");
		
		try {
			service.addContact(user.getTelefono(),bd1.longValue());
			assertTrue(service.getContacts(user.getTelefono()).contains(service.getUser(bd1.longValue())));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
	
	
	
    
    
    
}
