package edu.eci.arsw;

import static org.junit.Assert.assertArrayEquals;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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
import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.entities.Message;
import edu.eci.arsw.application.entities.StateEnum;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.entities.util.Line;
import edu.eci.arsw.application.entities.util.Point;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.persistence.DAO.ChatDAO;
import edu.eci.arsw.application.persistence.DAO.MessageDAO;
import edu.eci.arsw.application.persistence.DAO.UserDAO;
import edu.eci.arsw.application.persistence.impl.DrawPersistenceImpl;
import edu.eci.arsw.application.services.DrawChatService;
import edu.eci.arsw.application.services.impl.DrawChatServiceImpl;

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
	DrawChatServiceImpl service;

	@Autowired
	DrawPersistenceImpl persistence;

	@Autowired
	ChatDAO chatDAO;

	@Autowired
	MessageDAO messageDAO;

	@Autowired
	UserDAO userDAO;
	
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
		assertTrue(Long.toString(currentUserTelephone).equals("1291111111"));

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

	@Test
	public void shouldAddChat() {
		boolean check = false;
		User user = new User(1111111211, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1111111311, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		String telUser = "1111111211";
		String telUserContact = "1111111311";
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			List<Chat> chats = chatDAO.findAll();
			for (Chat c : chats) {
				String tel1 = Long.toString(c.getUser1().getTelefono());
				String tel2 = Long.toString(c.getUser2().getTelefono());
				if (telUser.equals(tel1) && telUserContact.equals(tel2)) {
					check = true;
				}
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertTrue(check);

	}

	@Test
	public void shouldGetChat() {

		boolean res = true;
		try {
			Chat c = service.getChat(1111111211, 1111111311);
			String f = Long.toString(c.getUser1().getTelefono());
			String s = Long.toString(c.getUser1().getTelefono());
			String tel1 = "1111111211";
			String tel2 = "1111111311";
			if (f.toString().equals(tel1.toString()) && s.toString().equals(tel2.toString())) {
				res = true;
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertTrue(res);
	}

	@Test
	public void shouldNotGetChatInexistentFirstUser() {

		try {
			Chat c = service.getChat(1811111211, 1111111311);
		} catch (AppException e) {
			assertTrue(true);
		}

	}

	@Test
	public void shouldNotGetChatInexistentSecondtUser() {
		try {
			Chat c = service.getChat(1111111211, 1111411211);
		} catch (AppException e) {
			assertTrue(true);
		}
	}

	@Test
	public void shouldAddChatAndGetChat() {
		boolean check = false;
		User user = new User(1111111511, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1111111611, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		String telUser = "1111111511";
		String telUserContact = "1111111611";
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			Chat chat = service.getChat(1111111511, 1111111611);
			String tel1 = Long.toString(chat.getUser1().getTelefono());
			String tel2 = Long.toString(chat.getUser2().getTelefono());
			if (telUser.equals(tel1) && telUserContact.equals(tel2)) {
				check = true;
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertTrue(check);
	}

	@Test
	public void shouldNotAddChatInexistentFirstUser() {
		User userContact = new User(1111111711, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		String telUser = "1111111591";
		String telUserContact = "1111111711";
		try {
			service.addUser(userContact);
			service.addChat(1111111591, userContact.getTelefono());
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataIntegrityViolationException ei) {
			assertTrue(true);
		}

	}

	@Test
	public void shouldNotAddChatInexistentSecondtUser() {
		User user = new User(1111111831, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		String telUser = "1111111591";
		String telUserContact = "1111111991";
		try {
			service.addUser(user);
			service.addChat(user.getTelefono(), 1111111991);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataIntegrityViolationException ei) {
			assertTrue(true);
		}
	}

	@Test
	public void shouldAddMessage() {
		boolean check = false;
		User user = new User(1221111511, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1221111611, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		String telUser = "1221111511";
		String telUserContact = "1221111611";
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			Chat chat = service.getChat(1221111511, 1221111611);
			service.addMessage(new Message(0, chat, user, "hola", new Date()));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Message me = messageDAO.findAll().get(0);

		assertEquals(1, me.getId() - 1);

	}

	@Test
	public void messageShouldHaveContent() {
		boolean check = false;
		User user = new User(1231111511, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1231111611, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			Chat chat = service.getChat(user.getTelefono(), userContact.getTelefono());
			service.addMessage(new Message(0, chat, user, "hola", new Date()));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Message> me = messageDAO.findAll();

		for (Message ms : me) {
			if (ms.getContenido().equals("hola")) {
				check = true;
			}
		}

		assertTrue(check);

	}

	@Test
	public void messageAddedShouldHaveEmisor() {
		boolean check = false;
		User user = new User(1291111511, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1291111611, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			Chat chat = service.getChat(user.getTelefono(), userContact.getTelefono());
			service.addMessage(new Message(0, chat, user, "hola", new Date()));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Message> me = messageDAO.findAll();

		for (Message ms : me) {
			if (Long.toString(ms.getEmisor().getTelefono()).equals("1291111511")) {
				check = true;
			}
		}

		assertTrue(check);
	}

	@Test
	public void messageAddedShouldHaveUsersChat() {
		boolean check = false;
		User user = new User(1271111511, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1271111611, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			Chat chat = service.getChat(user.getTelefono(), userContact.getTelefono());
			service.addMessage(new Message(0, chat, user, "hola", new Date()));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Message> me = messageDAO.findAll();

		for (Message ms : me) {
			if (Long.toString(ms.getChat().getUser1().getTelefono()).equals("1271111511")
					&& Long.toString(ms.getChat().getUser2().getTelefono()).equals("1271111611")) {
				check = true;
			}
		}

		assertTrue(check);
	}

	@Test
	public void messageIdShouldIncrement() {
		boolean check = false;
		User user = new User(1273111511, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1273111611, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			Chat chat = service.getChat(user.getTelefono(), userContact.getTelefono());
			service.addMessage(new Message(0, chat, user, "hola", new Date()));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Message> me = messageDAO.findAll();

		for (Message ms : me) {
			if (Long.toString(ms.getChat().getUser1().getTelefono()).equals("1273111511")
					&& Long.toString(ms.getChat().getUser2().getTelefono()).equals("1273111611") && ms.getId() >= 2) {
				check = true;
			}
		}

		assertTrue(check);
	}

	@Test
	public void shouldGetChatByTelephone() {
		boolean check = false;
		Chat ct = null;
		User user = new User(1293111511, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1293111611, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			List<Chat> chats = service.getChats(1293111511);
			ct = chats.get(0);

		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertTrue(1293111511 == ct.getUser1().getTelefono());
		// assertTrue(check);
	}

	@Test
	public void shouldNotGetChatByTelephone() {
		boolean check = false;
		Chat ct = null;
		User user = new User(1593111511, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1593111611, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			List<Chat> chats = service.getChats(1893111511);
			ct = chats.get(0);

		} catch (AppException e) {
			assertTrue(true);
		} catch (IndexOutOfBoundsException ei) {
			assertTrue(true);
		}

		// assertTrue(1293111511 == ct.getUser1().getTelefono());
		// assertTrue(check);
	}

	@Test
	public void souldIdofChatShouldIncrement() {
		List<Chat> chats = chatDAO.findAll();
		boolean increment = chats.size() >= 5;
		assertTrue(increment);
	}

	@Test
	public void shouldPastMessageHaveContent() {
		messageDAO.deleteAll();
		boolean check = true;
		User user = new User(1222222221, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1333333331, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			Chat chat = service.getChat(user.getTelefono(), userContact.getTelefono());
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Message> me = messageDAO.findAll();

		int cont = 0;
		for (Message ms : me) {
			if (!ms.getContenido().equals("mensaje")) {
				check = false;
			}

		}
		assertTrue(check);

	}

	@Test
	public void shouldPastMessageHaveContentAfterTenMessages() {
		boolean check = true;
		User user = new User(1444444441, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1555555551, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			Chat chat = service.getChat(user.getTelefono(), userContact.getTelefono());
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Message> me = messageDAO.findAll();

		for (Message ms : me) {
			if (!ms.getContenido().equals("mensaje")) {
				check = false;
			}

		}
		assertTrue(check);
	}

	@Test
	public void shouldPastMessageHaveContentAfterTwentyMessages() {
		boolean check = true;
		User user = new User(1666666661, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1777777771, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			Chat chat = service.getChat(user.getTelefono(), userContact.getTelefono());
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Message> me = messageDAO.findAll();

		for (Message ms : me) {
			if (!ms.getContenido().equals("mensaje")) {
				check = false;
			}

		}
		assertTrue(check);
	}

	@Test
	public void shouldGetChatMessages() {
		boolean check = true;
		User user = new User(1663366661, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact = new User(1772277771, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user);
			service.addUser(userContact);
			service.addChat(user.getTelefono(), userContact.getTelefono());
			Chat chat = service.getChat(user.getTelefono(), userContact.getTelefono());
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			service.addMessage(new Message(0, chat, user, "mensaje", new Date()));
			List<Message> msgs = service.getChatMessages(chat.getId());
			
			for(Message ms: msgs) {
				if(!ms.getContenido().equals("mensaje")) {
					check = false;
				}
			}
			
		}catch (AppException e) {
			// TODO: handle exception
		}
		
		assertTrue(check);
	}

	@Test 
	public void shouldNotGetChatMessages() {
		try {
			service.getChatMessages(3045);
		} catch (AppException e) {
			assertTrue(e.getMessage().equals(AppException.CHAT_NOT_EXISTS));
		}
	}
	 
	
	
	@Test
	public void shouldAddUserToGroup() {
		User user1 = new User(1661111122, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User user2 = new User(1661111133, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User user3 = new User(1661181133, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		boolean res = false;
		try {
			service.addUser(user1);
			service.addUser(user2);
			service.addContact(user1.getTelefono(), user2.getTelefono());
			Set<User> members = new HashSet<User>();
			Group grupo = new Group(123, "Prueba1", "Prueba1", new Date(), members);
			service.addGroup(user1.getTelefono(), grupo);
			Group group = service.getGroup("Prueba1");
			service.addUserToGroup(user1.getTelefono(), user2.getTelefono(), group);			
			Group temp = service.getGroup(grupo.getNombre());
			List<User> listTemp = new ArrayList<User>();
			listTemp.addAll(temp.getMembers());
			List<Long> numeros = new ArrayList<Long>();
			for(User u: listTemp) {
				numeros.add(u.getTelefono());
			}
			Set<User> membersTemp = new HashSet<User>();
			membersTemp.add(user1);
			membersTemp.add(user2);
			System.out.println(listTemp);
			System.out.println(membersTemp);
			res = numeros.contains(user1.getTelefono()) && numeros.contains(user2.getTelefono()); 
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(res);
	}
	@Test
	public void shouldAddUsersToGroup() {
		User user1 = new User(1661711122, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User user2 = new User(1669911133, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User user3 = new User(1662281133, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		boolean res = false;
		try {
			service.addUser(user1);
			service.addUser(user2);
			service.addUser(user3);
			service.addContact(user1.getTelefono(), user2.getTelefono());
			service.addContact(user1.getTelefono(), user3.getTelefono());
			Set<User> members = new HashSet<User>();
			Group grupo = new Group(125, "Prueba2", "Prueba2", new Date(), members);
			service.addGroup(user1.getTelefono(), grupo);
			
			Group grupoLlenado = service.getGroup("Prueba2"); 
			//service.addGroup(user1.getTelefono(), grupoLlenado);
			Set<User> usuarios = new HashSet<User>();
			usuarios.add(user2);
			usuarios.add(user3);
			grupoLlenado.setMembers(usuarios);
			
			service.addUsersToGroup(user1.getTelefono(), grupoLlenado);			
			Group temp = service.getGroup(grupo.getNombre());
			List<User> listTemp = new ArrayList<User>();
			listTemp.addAll(temp.getMembers());
			List<Long> numeros = new ArrayList<Long>();
			for(User u: listTemp) {
				numeros.add(u.getTelefono());
			}
			Set<User> membersTemp = new HashSet<User>();
			membersTemp.add(user1);
			membersTemp.add(user2);
			System.out.println(listTemp);
			System.out.println(membersTemp);
			System.out.println(grupoLlenado.getMembers());
			res = numeros.contains(user1.getTelefono()) && numeros.contains(user2.getTelefono()) && numeros.contains(user3.getTelefono()); 
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(res);
	}
	
	@Test
	public void shouldGetContactsFromGroup() {
		User user1 = new User(1662211122, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User user2 = new User(1669311133, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User user3 = new User(1662182133, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		boolean res = false;
		try {
			service.addUser(user1);
			service.addUser(user2);
			service.addUser(user3);
			service.addContact(user1.getTelefono(), user2.getTelefono());
			service.addContact(user1.getTelefono(), user3.getTelefono());
			Set<User> members = new HashSet<User>();
			//members.add(user2);
			//members.add(user3);
			Group grupo = new Group(130, "Prueba3", "Prueba3", new Date(), members);
			service.addGroup(user1.getTelefono(), grupo);
			service.addUsersToGroup(user2.getTelefono(), grupo);
			service.addUsersToGroup(user3.getTelefono(), grupo);
			
			Group temp = service.getGroup(grupo.getNombre());
			List<User> listTemp = service.getContactsExGroup(user1.getTelefono(), 130);
			List<Long> numeros = new ArrayList<Long>();
			for(User u: listTemp) {
				numeros.add(u.getTelefono());
			}
			
			res = numeros.contains(user2.getTelefono()) && numeros.contains(user3.getTelefono());
			
			System.out.println(service.getContactsExGroup(user1.getTelefono(), 130));
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		assertTrue(res);
	}
	
	@Test
	public void shouldDeleteUserFromGroup() {
		User user1 = new User(1682211122, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	User user2 = new User(1679311133, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	User user3 = new User(1612182133, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	boolean res = true;
	 	boolean resEliminado = false;
	 	try {
	 		service.addUser(user1);
	 		service.addUser(user2);
	 		service.addUser(user3);
	 		service.addContact(user1.getTelefono(), user2.getTelefono());
	 		service.addContact(user1.getTelefono(), user3.getTelefono());
	 		Set<User> members = new HashSet<User>();
			Group grupo = new Group(135, "Prueba4", "Prueba4", new Date(), members);
			
	 		service.addGroup(user1.getTelefono(), grupo);
			Group temp = service.getGroup("Prueba4");
	 		service.addUserToGroup(user1.getTelefono(), user2.getTelefono(), temp);
	 		service.addUserToGroup(user1.getTelefono(), user3.getTelefono(), temp);
			
			
			
	 		service.deleteUserFromGroup(user1.getTelefono(), user3.getTelefono(), grupo);
	 		Set<User> listTemp = service.getGroup("Prueba4").getMembers();
	 		List<Long> numeros = new ArrayList<Long>();
	 		for(User u: listTemp) {
	 			numeros.add(u.getTelefono());
	 		}
			
			
	 		res = numeros.contains(user2.getTelefono()) && numeros.contains(user3.getTelefono());
			
	 		Set<User> setTemp = persistence.getGroupById(135).getMembers();
	 		System.out.println(setTemp);
	 		List<User> listTempElminado = new ArrayList<User>();
	 		listTempElminado.addAll(setTemp);
	 		List<Long> numerosEliminado = new ArrayList<Long>();
	 		for(User u: listTempElminado) {
	 			numerosEliminado.add(u.getTelefono());
	 		}
			
	 		resEliminado = numerosEliminado.contains(user2.getTelefono());
			
	 	}catch (Exception e) {
	 		System.out.println(e.getStackTrace());
	 	}
	 	assertTrue(res);
	 }
	

	@Test
	public void shouldDeleteMessage() {
		User user1 = new User(1663211122, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User user2 = new User(1662211133, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User user3 = new User(1612181133, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		boolean res = false;
		boolean resBorrado = false;
		try {
			service.addUser(user1);
			service.addUser(user2);
			service.addUser(user3);
			service.addChat(user1.getTelefono(), user2.getTelefono());
			Chat chat = service.getChat(user1.getTelefono(), user2.getTelefono());
			Message mensaje = new Message(147, chat,user1, "Hola", new Date());
			service.addMessage(mensaje);
			service.deleteMessage(mensaje);
			List<Message> lista = service.getChatMessages(chat.getId());
			res = !lista.contains(mensaje);
		}catch (Exception e) {
			// TODO: handle exception
		}
			
		assertTrue(res);
	}
	
	@Test
	public void shouldGetGroupById(){
		User user1 = new User(1661719922, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User user2 = new User(1669922133, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User user3 = new User(1632181133, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		boolean res = false;
		try {
			service.addUser(user1);
			service.addUser(user2);
			service.addUser(user3);
			service.addContact(user1.getTelefono(), user2.getTelefono());
			service.addContact(user1.getTelefono(), user3.getTelefono());
			Set<User> members = new HashSet<User>();
			Group grupo = new Group(1, "Prueba7", "Prueba7", new Date(), members);
			service.addGroup(user1.getTelefono(), grupo);
			Group groupTemp = service.getGroupById(service.getGroup("Prueba7").getId());
			res = groupTemp.getNombre().equals("Prueba7");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		assertTrue(res);
	}
	
	@Test(expected = NullPointerException.class)
	public void shouldCreateNewSession() {
		User user1 = new User(1661710922, // telefono,
				"Prueba", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user1);
			Set<User> members = new HashSet<User>();
			Group grupo = new Group(1, "Prueba10", "Prueba10", new Date(), members);
			service.addGroup(user1.getTelefono(), grupo);
			Message msg1 = new Message(0, service.getGroup("Prueba10"), user1, "hola a todos", new Date());
			service.addMessage(msg1);
			Line linea = new Line();
			Point start = new Point();
			Point end = new Point();
			start.setX(0);
			start.setY(10);
			end.setX(20);
			end.setY(20);
			linea.setColor("red");
			linea.setStart(start);
			linea.setEnd(end);
			service.addDrawSession(service.getGroup("Prueba10"));
			service.saveDrawLine(service.getGroup("Prueba10").getId(), linea);
			service.createNewSession(service.getGroup("Prueba10").getId());
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void shouldBelongMemberToGroup() {
		User user1 = new User(1682411122, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	User user2 = new User(1679411133, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	User user3 = new User(1612482133, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	boolean res = false;
	 	try {
	 		service.addUser(user1);
	 		service.addUser(user2);
	 		service.addUser(user3);
	 		service.addContact(user1.getTelefono(), user2.getTelefono());
	 		service.addContact(user1.getTelefono(), user3.getTelefono());
	 		Set<User> members = new HashSet<User>();
			Group grupo = new Group(139, "Prueba15", "Prueba15", new Date(), members);
			
	 		service.addGroup(user1.getTelefono(), grupo);
			Group temp = service.getGroup("Prueba15");
	 		service.addUserToGroup(user1.getTelefono(), user2.getTelefono(), temp);
	 		service.addUserToGroup(user1.getTelefono(), user3.getTelefono(), temp);
	 		res = service.belongMemberToGroup(user2.getTelefono(), service.getGroup("Prueba15"));
	 	}catch (Exception e) {
			// TODO: handle exception
		}
	 	assertTrue(res);
	}
	
	@Test
	public void shouldBelongAdminToGroup() {
		User user1 = new User(1282411122, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	User user2 = new User(1579411133, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	User user3 = new User(1312382133, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	boolean res = false;
	 	try {
	 		service.addUser(user1);
	 		service.addUser(user2);
	 		service.addUser(user3);
	 		service.addContact(user1.getTelefono(), user2.getTelefono());
	 		service.addContact(user1.getTelefono(), user3.getTelefono());
	 		Set<User> members = new HashSet<User>();
			Group grupo = new Group(139, "Prueba20", "Prueba20", new Date(), members);
			
	 		service.addGroup(user1.getTelefono(), grupo);
			Group temp = service.getGroup("Prueba20");
	 		service.addUserToGroup(user1.getTelefono(), user2.getTelefono(), temp);
	 		service.addUserToGroup(user1.getTelefono(), user3.getTelefono(), temp);
	 		res = service.belongAdminToGroup(user1.getTelefono(), service.getGroup("Prueba20"));
	 	}catch (Exception e) {
			// TODO: handle exception
		}
	 	assertTrue(res);
	}
	
	@Test
	public void shouldDeleteUsersFromGroup() {
		User user1 = new User(1282411121, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	User user2 = new User(1579411131, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	User user3 = new User(1312382131, // telefono,
	 			"Prueba", // nombre,
	 			"Prueba", // apellido,
	 			"abcdefg", // contraseña,
	 			new Date(), // fecharegistro,
	 			new Date(), // fechaconexion,
	 			StateEnum.DISCONNECTED.toString()/* estado */);
	 	boolean res = false;
	 	try {
	 		service.addUser(user1);
	 		service.addUser(user2);
	 		service.addUser(user3);
	 		service.addContact(user1.getTelefono(), user2.getTelefono());
	 		service.addContact(user1.getTelefono(), user3.getTelefono());
	 		Set<User> members = new HashSet<User>();
			Group grupo = new Group(139, "Prueba25", "Prueba25", new Date(), members);
			
	 		service.addGroup(user1.getTelefono(), grupo);
			Group temp = service.getGroup("Prueba25");
	 		service.addUserToGroup(user1.getTelefono(), user2.getTelefono(), temp);
	 		service.addUserToGroup(user1.getTelefono(), user3.getTelefono(), temp);
	 		service.deleteUsersFromGroup(user3.getTelefono(), service.getGroup("Prueba25"));
	 		res = service.belongMemberToGroup(user3.getTelefono(), service.getGroup("Prueba25"));
	 	}catch (Exception e) {
			// TODO: handle exception
		}
	 	assertTrue(!res);
	}
	
	@Test
	public void shouldNotAddUserToGroup() {
		
	}

	@Test
	public void shouldUpgradeUsersOnGroup() {
		User user = new User(1661111111, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact1  = new User(1631111111, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userContact2  = new User(1561111111, // telefono,
				"Ximena", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User newUser  = new User(1331111111, // telefono,
				"Argemiro", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User lastUser  = new User(1531115111, // telefono,
				"Pedro", // nombre,
				"Infante", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
				service.addUser(user);
				service.addUser(userContact1);
				service.addUser(userContact2);
				//service.addUser(userContact3);
				service.addContact(user.getTelefono(), userContact1.getTelefono());
				service.addContact(user.getTelefono(), userContact2.getTelefono());
				//service.addContact(user.getTelefono(), userContact3.getTelefono());
				// List<User> usuariosTemp = service.getContacts(user.getTelefono());
				// System.out.println(usuariosTemp);
				String nombre = "grupo de apoyo";
				Set<User> members = new HashSet<>();
				//members.add(userContact1);
				//members.add(userContact2);
				//members.add(userContact3);
				Group grupo = new Group(0, "grupo de apoyo", "te ayudamos con apoyo", new Date(),members);
				service.addGroup(user.getTelefono(),grupo);
				System.out.println("ok");
				Group grp = service.getGroup(nombre);
				System.out.println(grp);
				Message msg1 = new Message(0, grp, user, "hola a todos", new Date());
				service.addMessage(msg1);
				System.out.println("ok msg1");

				service.addUser(newUser);
				service.addContact(user.getTelefono(), newUser.getTelefono());
				service.addContact(newUser.getTelefono(), userContact1.getTelefono());
				service.addContact(newUser.getTelefono(), userContact2.getTelefono());
				service.addUserToGroup(user.getTelefono(), newUser.getTelefono(), grp);

				Message msg2 = new Message(0, grp, newUser, "soy el nuevo", new Date());
				service.addMessage(msg2);
				System.out.println("ok msg2");

				Group grp2 = service.getGroup(nombre);
				System.out.println(grp2.getMembers());
				System.out.println(service.getGroupChatMessages(grp2.getId()));
				Set<User> newUsers = new HashSet<>();
				newUsers.add(userContact1); 
				newUsers.add(userContact2); 
				grupo.setMembers(newUsers);

				service.upgradeUserOnGroup(user.getTelefono(), newUser.getTelefono(), grupo);

				//service.upgradeUserOnGroup(newUser.getTelefono(), userContact1.getTelefono(), grupo);

				service.addUsersToGroup(newUser.getTelefono(),grupo);

				Set<User> upUsers = new HashSet<>();
				upUsers.add(userContact1); 
				upUsers.add(userContact2); 
				grupo.setMembers(upUsers);

				service.upgradeUsersOnGroup(user.getTelefono(),grupo);

				service.addUser(lastUser);
				service.addContact(userContact1.getTelefono(), lastUser.getTelefono());

				service.addUserToGroup(userContact1.getTelefono(), lastUser.getTelefono(), grupo);

				//System.out.println(service.getContactsExGroup(user.getTelefono(), grp2.getId()));
				
				Group grp3 = service.getGroup(nombre);
				System.out.println("antes");
				System.out.println(grp3.getMembers());

				Message msg3 = new Message(0, grp3, userContact1, "hola nuevo", new Date());
				Message msg4 = new Message(0, grp3, newUser, "soy el nuevo", new Date());
				Message msg5 = new Message(0, grp3, newUser, "soy el nuevo", new Date());
				Message msg6 = new Message(0, grp3, newUser, "soy el nuevo", new Date());
				Message msg7 = new Message(0, grp3, newUser, "soy el nuevo", new Date());
				//service.addMessage(msg2);
				//System.out.println("ok msg2");

				//service.upgradeUserOnGroup(user.getTelefono(), userContact1.getTelefono(), grupo);
				//System.out.println("de nuevo");
				//service.upgradeUserOnGroup(userContact1.getTelefono(), userContact2.getTelefono(), grupo);
				//service.upgradeUserOnGroup(tUsuario1, tUsUp, grupo);


				//service.deleteUserFromGroup(user.getTelefono(), userContact1.getTelefono(), grupo);
				//Set<User> delUsers = new HashSet<>();
				//delUsers.add(userContact1); 
				//delUsers.add(newUser);
				//grupo.setMembers(delUsers);
				//service.deleteUsersFromGroup(user.getTelefono(), grupo);
				
				Group grp4 = service.getGroup(nombre);
				System.out.println("despues");
				System.out.println(grp4.getMembers());

		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
			
}
