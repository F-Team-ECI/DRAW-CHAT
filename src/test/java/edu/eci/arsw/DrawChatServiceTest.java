package edu.eci.arsw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ActiveProfiles;

import edu.eci.arsw.application.DrawChatApp;
import edu.eci.arsw.application.entities.StateEnum;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.persistence.impl.DrawPersistenceImpl;
import edu.eci.arsw.application.services.DrawChatService;


/**
 * Principal unit test App services
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DrawChatApp.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) 
public class DrawChatServiceTest {
	
	@Autowired
	DrawChatService service;
	
	@Autowired
	DrawPersistenceImpl persistence;

	@Test
    public void shouldNotBeUsers() {
    	try {
    		
			assertEquals(0,service.getUsers().size());
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Test 
	public void ItShouldThreeUsers() {
		List<User> users = null;
		
		User user1 = new User();
		user1.setTelefono(1111111104);
		user1.setNombre("prueba1");
		user1.setApellido("prueba1");
		user1.setContraseña("sadasdasdas");
		User user2 = new User();
		user2.setTelefono(1111111105);
		user2.setNombre("prueba2");
		user2.setApellido("prueba2");
		user2.setContraseña("sadasdasdas");
		User user3 = new User();
		user3.setTelefono(1111111106);
		user3.setNombre("prueba3");
		user3.setApellido("prueba3");
		user3.setContraseña("sadasdasdas");
		
		try {
			service.addUser(user1);
			service.addUser(user2);
			service.addUser(user3);
			users = service.getUsers();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(3, users.size());
	}
	
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
    public void notaddUserIncorrectCredential() throws AppException {
		User user = new User();
		user.setTelefono(111111111);
		user.setNombre("prueba");
		user.setApellido("prueba");
		user.setContraseña("sadasdasdas");
		service.addUser(user);
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
    public void addUserWithoutNombre() throws AppException {
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
    public void addUserAddContact() {
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
	
	@Test 
	public void usersAddedShouldBe() {
		User user1 = new User();
		user1.setTelefono(1111111101);
		user1.setNombre("prueba1");
		user1.setApellido("prueba1");
		user1.setContraseña("sadasdasdas");
		User user2 = new User();
		user2.setTelefono(1111111102);
		user2.setNombre("prueba2");
		user2.setApellido("prueba2");
		user2.setContraseña("sadasdasdas");
		User user3 = new User();
		user3.setTelefono(1111111103);
		user3.setNombre("prueba3");
		user3.setApellido("prueba3");
		user3.setContraseña("sadasdasdas");
		
		List<User> hechos = new ArrayList<User>();
		hechos.add(user1);
		hechos.add(user2);
		hechos.add(user3);
		
		List<User> obtenidos = new ArrayList<User>();
		
		try {
			service.addUser(user1);
			service.addUser(user2);
			service.addUser(user3);
			obtenidos.add(service.getUser(1111111101));
			obtenidos.add(service.getUser(1111111102));
			obtenidos.add(service.getUser(1111111103));
			

		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(obtenidos.toString().equals(hechos.toString()));
		
		
	}
	
	@Test 
	public void userDoesNotHaveContacts() {
		List<User> contacts = null;
		try {
			contacts = service.getContacts(1111111101);
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, contacts.size());
	}
	
	@Test 
	public void userHasAnContact() {
		List<User> contacts = null;
		
		User user1 = new User();
		user1.setTelefono(1111111001);
		user1.setNombre("prueba1");
		user1.setApellido("prueba1");
		user1.setContraseña("sadasdasdas");
		
		User user2 = new User();
		user2.setTelefono(1111111002);
		user2.setNombre("prueba2");
		user2.setApellido("prueba2");
		user2.setContraseña("sadasdasdas");
		
		try {
			service.addUser(user1);
			service.addUser(user2);
			service.addContact(1111111001, 1111111002);
			contacts = service.getContacts(1111111001);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1, contacts.size());

	}
	
	@Test 
	public void userShouldHaveTenContacts() {
		List<User> contacts = null;
		
		User user = new User();
		user.setTelefono(1111111109);
		user.setNombre("pruebaTen");
		user.setApellido("pruebaTen");
		user.setContraseña("pruebaTen");		
		
		try {
			service.addUser(user);
			for(int i = 0; i<10; i++) {
				String tel = "111111000" + Integer.toString(i);
				User userTemp = new User();
				userTemp.setTelefono(Long.parseLong(tel));
				userTemp.setNombre(tel);
				userTemp.setApellido(tel);
				userTemp.setContraseña(tel);
				service.addUser(userTemp);
				service.addContact(1111111109, Long.parseLong(tel));
			}
			contacts = service.getContacts(user.getTelefono());
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(10, contacts.size());
		
	}
	
	@Test 
	public void userAlreadyRegistered() {
		User user = new User();
		user.setTelefono(1111111111);
		user.setNombre("prueba");
		user.setApellido("prueba");
		user.setContraseña("prueba");
		try {
			service.addUser(user);
			service.addUser(user);
		} catch (AppException e) {
			assertTrue(e.getMessage().equals("User already registered"));
		}
	}
	
	@Test 
	public void addUserWithOutLastName() {
		User user = new User();
		user.setTelefono(1100111111);
		user.setNombre("prueba");
		user.setContraseña("prueba");
		try {
			service.addUser(user);
		} catch (AppException e) {
			assertTrue(e.getMessage().equals("Incorrect credentials"));
		}
	}
	
	@Test 
	public void addUserWithOutPassword() {
		User user = new User();
		user.setTelefono(1111100111);
		user.setNombre("prueba");
		user.setApellido("prueba");
		try {
			service.addUser(user);
		} catch (AppException e) {
			assertTrue(e.getMessage().equals("Incorrect credentials"));
		}
	}
	
	@Test(expected = NullPointerException.class)
	public void addInvalidUser() {
		try {
			service.addUser(null);
		} catch (AppException e) {
			assertTrue(e.getMessage().equals("Invalid user"));
		}
	}
	
	//Por hacer
	@Test 
	public void getMessageFromAChat() {
	}
	
	/*BORRAR ESTO*/
	@Test 
	public void produccionTest() {

		User user =new User(1111111111,//telefono, 
							"Andres",//nombre, 
							"Carreto",//apellido, 
							"serdan",//contraseña, 
							new Date(),//fecharegistro, 
							new Date(),//fechaconexion, 
							StateEnum.DISCONNECTED.toString()/*estado*/);
		System.out.println("Nuevo Usuario");
		System.out.println(user);
		User upUser =new User(1111111111,//telefono, 
							"An",//nombre, 
							"Orj",//apellido, 
							" ",//contraseña, 
							null,//fecharegistro, 
							null,//fechaconexion, 
							StateEnum.ONLINE.toString()/*estado*/);
		System.out.println("Update Usuario");
		System.out.println(upUser);
		try {
			service.addUser(user);
			System.out.println("Antes");
			System.out.println(service.getUser(user.getTelefono()));
			service.updateUser(upUser);
			System.out.println("Despues");
			System.out.println(service.getUser(upUser.getTelefono()));
		} catch (Exception e) {
			System.out.println("Hubo un problema");
			e.printStackTrace();
		}
	}
    
}
