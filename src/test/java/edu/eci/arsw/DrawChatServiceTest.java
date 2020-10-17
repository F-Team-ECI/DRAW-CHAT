package edu.eci.arsw;

import static org.junit.Assert.assertArrayEquals;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ActiveProfiles;

import edu.eci.arsw.application.DrawChatApp;
import edu.eci.arsw.application.entities.Chat;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class DrawChatServiceTest {

	@Autowired
	DrawChatService service;

	@Autowired
	DrawPersistenceImpl persistence;
	
	@Test
	public void cargar() throws AppException {
		User user = new User(1291111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		service.addUser(user);
	}

	@Test
	public void addEmptyUsers() {
		try {

			assertEquals(0, service.getUsers().size());

		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void addThreeUsers() {
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
			for (User u : service.getUsers()) {
				if (u.getTelefono() == user.getTelefono())
					temp = true;
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
			service.addContact(bd1.longValue(), bd0.longValue());
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
	@WithMockUser(username = "1291111111", password = "pwd", roles = "USER")
	public void getCurrentSessionWoldBeRun() throws AppException {
		long currentUserTelephone = service.getCurrentUserSession().getTelefono();
			assertTrue(
					Long
					.toString(currentUserTelephone)
					.equals(
					"1291111111"));
		
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
			service.addContact(user.getTelefono(), bd1.longValue());
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
			for (int i = 0; i < 10; i++) {
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

	// Por hacer
	@Test
	public void getMessageFromAChat() {
	}

	@Test
	public void updateTest() {

		User user = new User(1111111111, // telefono,
				"Andres", // nombre,
				"Carreto", // apellido,
				"serdan", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User upUser = new User(1111111111, // telefono,
				"An", // nombre,
				"Orj", // apellido,
				" ", // contraseña,
				null, // fecharegistro,
				null, // fechaconexion,
				StateEnum.ONLINE.toString()/* estado */);
		try {
			service.addUser(user);
			service.updateUser(upUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotUpdateUserNombre() {
		User user = new User(1811111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userUpdate = new User(1811111111, // telefono,
				"ab", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		
		try {
			service.addUser(user);
			service.updateUser(userUpdate);
			assertTrue(service.getUser(1811111111).getNombre().equals("Julian"));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void shouldNotUpdateUserApellido() {
		User user = new User(1911111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userUpdate = new User(1911111111, // telefono,
				"Julian", // nombre,
				"Pr", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		
		try {
			service.addUser(user);
			service.updateUser(userUpdate);
			assertTrue(service.getUser(1911111111).getApellido().equals("Prueba"));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotUpdateUserContraseña() {
		User user = new User(1311111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userUpdate = new User(1311111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"ab", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		
		try {
			service.addUser(user);
			service.updateUser(userUpdate);
			assertTrue(service.getUser(1311111111).getContraseña().equals("abcdefg"));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotUpdateUserEstado() {
		User user = new User(1521111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userUpdate = new User(1521111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"ab", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				null/* estado */);
		
		try {
			service.addUser(user);
			service.updateUser(userUpdate);
			assertTrue(service.getUser(1521111111).getEstado().toString().equals("DISCONNECTED"));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldUpdateUserAndDelete() {
		User user = new User(1531111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userUpdate = new User(1531111111, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.updateUser(userUpdate);
			service.deleteUser(1531111111);
			service.getUser(1531111111);
		} catch (AppException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void shouldAddUsersAddContactAndUpdateUser() {
		User user = new User(1541111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1551111111, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContactUpdate = new User(1551111111, // telefono,
				"Andres", // nombre,
				"Lopez", // apellido,
				"123456789", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addContact(user.getTelefono(), userContact.getTelefono());
			service.updateUser(userContactUpdate);
			List<User> usuariosTemp = service.getContacts(user.getTelefono());
			User userTemp = usuariosTemp.get(0);
			userTemp.getNombre().equals("Andres");
			userTemp.getApellido().equals("Lopez");
			userTemp.getContraseña().equals("123456789");
			userTemp.getEstado().toString().equals("DISCONNECTED");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldAddUsersAddContactAndNotUpdateUserNombre() {
		User user = new User(1631111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1641111111, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContactUpdate = new User(1641111111, // telefono,
				"An", // nombre,
				"Lopez", // apellido,
				"123456789", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addContact(user.getTelefono(), userContact.getTelefono());
			service.updateUser(userContactUpdate);
			List<User> usuariosTemp = service.getContacts(user.getTelefono());
			User userTemp = usuariosTemp.get(0);
			userTemp.getNombre().equals("Federico");
			userTemp.getApellido().equals("Lopez");
			userTemp.getContraseña().equals("123456789");
			userTemp.getEstado().toString().equals("DISCONNECTED");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldAddUsersAddContactAndNotUpdateUserApellido() {
		User user = new User(1511111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1521111111, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContactUpdate = new User(1521111111, // telefono,
				"Andres", // nombre,
				"Lo", // apellido,
				"123456789", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addContact(user.getTelefono(), userContact.getTelefono());
			service.updateUser(userContactUpdate);
			List<User> usuariosTemp = service.getContacts(user.getTelefono());
			User userTemp = usuariosTemp.get(0);
			userTemp.getNombre().equals("Andres");
			userTemp.getApellido().equals("Prueba");
			userTemp.getContraseña().equals("123456789");
			userTemp.getEstado().toString().equals("DISCONNECTED");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldAddUsersAddContactAndNotUpdateUserContraseña() {
		User user = new User(1771111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1721111111, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContactUpdate = new User(1721111111, // telefono,
				"Andres", // nombre,
				"Lopez", // apellido,
				"1", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addContact(user.getTelefono(), userContact.getTelefono());
			service.updateUser(userContactUpdate);
			List<User> usuariosTemp = service.getContacts(user.getTelefono());
			User userTemp = usuariosTemp.get(0);
			userTemp.getNombre().equals("Andres");
			userTemp.getApellido().equals("Lopez");
			userTemp.getContraseña().equals("abcdefg");
			userTemp.getEstado().toString().equals("DISCONNECTED");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldAddUserAddContactAndNotUpdateUserEstado() {
		User user = new User(1791111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1341111111, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContactUpdate = new User(1341111111, // telefono,
				"Andres", // nombre,
				"Lopez", // apellido,
				"123456789", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				null/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addContact(user.getTelefono(), userContact.getTelefono());
			service.updateUser(userContactUpdate);
			List<User> usuariosTemp = service.getContacts(user.getTelefono());
			User userTemp = usuariosTemp.get(0);
			userTemp.getNombre().equals("Andres");
			userTemp.getApellido().equals("Lopez");
			userTemp.getContraseña().equals("123456789");
			userTemp.getEstado().toString().equals("DISCONNECTED");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldAddUserAddContactUpdate() {
		User user = new User(1661111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1631111111, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContactUpdate = new User(1631111111, // telefono,
				"Andres", // nombre,
				"Lopez", // apellido,
				"123456789", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.updateUser(userContactUpdate);
			service.addContact(user.getTelefono(), userContact.getTelefono());
			List<User> usuariosTemp = service.getContacts(user.getTelefono());
			User userTemp = usuariosTemp.get(0);
			userTemp.getNombre().equals("Andres");
			userTemp.getApellido().equals("Lopez");
			userTemp.getContraseña().equals("123456789");
			userTemp.getEstado().toString().equals("DISCONNECTED");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//BORRAR ESTO
	@Test
	public void producttest() {
		User user = new User(1661111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1631111111, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addContact(user.getTelefono(), userContact.getTelefono());
			List<User> usuariosTemp = service.getContacts(user.getTelefono());
			System.out.println(usuariosTemp);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			System.out.println("ok");
			Chat chat = service.getChat(user.getTelefono(), userContact.getTelefono());
			System.out.println(chat);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
