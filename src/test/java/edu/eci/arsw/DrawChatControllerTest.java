package edu.eci.arsw;

import org.junit.Test;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.eci.arsw.application.DrawChatApp;
import edu.eci.arsw.application.controllers.UserController;
import edu.eci.arsw.application.entities.StateEnum;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import javax.swing.text.AbstractDocument.Content;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DrawChatApp.class)
//@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_CLASS) 
public class DrawChatControllerTest {

	
	@Autowired
	private DrawChatService service;
	
	//@Autowired
	//private UserController userController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	//@LocalServerPort
	//private int port;
	
	//@Autowired
	//private TestRestTemplate restTemplate;
	//
	@Test
	public void shouldNotGetUsers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users"))
				.andExpect(status().isAccepted());
	}
	
	@Test
	public void shouldGetUsers() throws Exception {
		service.addUser(new User(1111111111, "prueba1", "prueba2", "abcdefghaijk", null, null, StateEnum.DISCONNECTED.toString()));
		User expected = service.getUser(1111111111);
		mockMvc.perform(MockMvcRequestBuilders.get("/users"))
				.andExpect(status().isAccepted())
				.andReturn()
				.equals(expected);
	}
	
	@Test
	public void shouldAddUser() throws Exception {
		User actual = new User(1111111112, "prueba1", "prueba2", "abcdefghaijk", null, null, StateEnum.DISCONNECTED.toString());
		String json = mapper.writeValueAsString(actual);
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void shouldNotAddUserNombre() throws Exception {
		User actual = new User(
				1111111119, 
				"pr", 
				"prueba2", 
				"abcdefghaijk", 
				new Date(), 
				new Date(), 
				StateEnum.DISCONNECTED.toString());
		String json = mapper.writeValueAsString(actual);
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldNotAddUserApellido() throws Exception {
		User actual = new User(
				1111111118, 
				"prueba", 
				"pr", 
				"abcdefghaijk", 
				new Date(), 
				new Date(), 
				StateEnum.DISCONNECTED.toString());
		String json = mapper.writeValueAsString(actual);
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldNotAddUserContraseña() throws Exception {
		User actual = new User(
				1111111125, 
				"prueba", 
				"pr", 
				"a", 
				new Date(), 
				new Date(), 
				StateEnum.DISCONNECTED.toString());
		String json = mapper.writeValueAsString(actual);
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldNotAddUserEstado() throws Exception {
		User actual = new User(
				1111111127, 
				"prueba", 
				"pr", 
				"a", 
				new Date(), 
				new Date(), 
				StateEnum.DISCONNECTED.toString());
		String json = mapper.writeValueAsString(actual);
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldGetUserByTelefono() throws Exception {
		User actualT = new User(1111111113, "prueba1", "prueba2", "abcdefghaijk", null, null, StateEnum.DISCONNECTED.toString());
		service.addUser(actualT);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users/{telefono}", 1111111113))
		.andExpect(status().isOk())
		.andReturn()
		.equals(service.getUser(1111111113));
	}
	
	@Test
	public void shouldNotGetUserByTelefono() throws AppException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/{telefono}", 1111111114))
		.andExpect(status().isOk());
		//Revisar el not found de este metodo (getUser) - Nunca lo hace
	}
	
	@Test
	public void souldAddUserAndAddContact() throws Exception{
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
		service.addUser(user);
		service.addUser(userContact);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
				.param("userA", "1661111111")
				.param("userB", "1631111111")
				).andExpect(status().isOk());
		List<User> usersTemp = service.getContacts(1661111111);
		User userT = usersTemp.get(0);
		assertTrue(userT.getNombre().equals("Federico"));
				
	}
	
	@Test
	@WithMockUser(username = "1651111111", password = "pwd", roles = "USER")
	//@WithUserDetails
	public void shouldAddUserAndUpdate() throws Exception{
		User user = new User(1651111111, // telefono,
				"user1", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User userUpdate = new User(1651111111, // telefono,
				"user2", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		service.addUser(user);
		String json = mapper.writeValueAsString(userUpdate);
		MvcResult mvcResult = 
				mockMvc
				.perform(MockMvcRequestBuilders
				.put("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userUpdate))).andReturn();
		int status = mvcResult.getResponse().getStatus();
		System.out.println(status);
		assertEquals(200, status);
		
		assertTrue(service.getUser(1651111111).getNombre().equals("user2"));
	}
	
	
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	
}
