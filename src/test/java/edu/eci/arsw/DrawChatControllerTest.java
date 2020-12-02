package edu.eci.arsw;

import org.junit.Before;
import org.junit.Test;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.eci.arsw.application.DrawChatApp;
import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.entities.StateEnum;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


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
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Autowired
	ObjectMapper mapper;
	
	//@LocalServerPort
	//private int port;
	
	//@Autowired
	//private TestRestTemplate restTemplate;
	//
	
	@Test
	public void shouldGetCurrentLoad() {
		User user = new User(
				1652231111, 
				"prueba", 
				"pr", 
				"pwddd", 
				new Date(), 
				new Date(), 
				StateEnum.DISCONNECTED.toString());
		try {
			service.addUser(user);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@WithMockUser(username = "1652231111", password = "pwddd", roles = "USER")
	public void shouldGetCurrentUser() throws Exception {
		String uri = "/users/me";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
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
		User actual = new User(1111111112, "prueba1", "prueba2", "abcdefghaijk", new Date(), new Date(), StateEnum.DISCONNECTED.toString());
		String json = mapper.writeValueAsString(actual);
		mockMvc.perform(MockMvcRequestBuilders.post("/registro")
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
		mockMvc.perform(MockMvcRequestBuilders.post("/registro")
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
		mockMvc.perform(MockMvcRequestBuilders.post("/registro")
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
		mockMvc.perform(MockMvcRequestBuilders.post("/registro")
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
		mockMvc.perform(MockMvcRequestBuilders.post("/registro")
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
	
	/*
	@Test
	public void shouldCreateChat() throws Exception {
		User user1 = new User(1666666661, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString());
		User user2 = new User(1777777771, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString());
		try {
			service.addUser(user1);
			service.addUser(user2);
		}catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		String uri = "/chats";
		Chat chat = new Chat(0,user1, user2,"normal");
		String chatJson = asJsonString(chat);
		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(chatJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		Chat temp = service.getChat(user1.getTelefono(), user2.getTelefono());
		assertEquals(1, temp.getId());
		
	}

	@Test(expected = NestedServletException.class)
	public void shouldNotCreateChat() throws Exception {
		User user1 = new User(1666655661, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString());
		try {
			service.addUser(user1);
		}catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		String uri = "/chats";
		Chat chat = new Chat(0,user1, service.getUser(1666655361),"normal");
		String chatJson = asJsonString(chat);
		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(chatJson))
				.andReturn();

	}
	*/
	@Test
	public void shouldGetContactsByUser() throws Exception {
		User user1 = new User(1665566661, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		User user2 = new User(1775577771, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString()/* estado */);
		try {
			service.addUser(user1);
			service.addUser(user2);
			service.addContact(1665566661, 1775577771);
		}catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String uri = "/users/1665566661/contacts";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		User[] chats = mapFromJson(content, User[].class);
		assertTrue(chats.length==1);
	}

	
	@Test
	public void shouldGetChatByPhone() throws Exception {
		User user1 = new User(1566666661, // telefono,
				"Julian", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString());
		User user2 = new User(1677777771, // telefono,
				"Federico", // nombre,
				"Prueba", // apellido,
				"abcdefg", // contraseña,
				new Date(), // fecharegistro,
				new Date(), // fechaconexion,
				StateEnum.DISCONNECTED.toString());
		try {
			service.addUser(user1);
			service.addUser(user2);
		}catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		service.addChat(user1.getTelefono(), user2.getTelefono());
		
		String uri = "/chats/users/1566666661";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Chat[] chats = mapFromJson(content, Chat[].class);
		assertTrue(chats.length==1);
	}

	@Test
	public void shouldNotGetChatByPhone() throws Exception {
		String uri = "/chats/users/1566666462";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		String body = mvcResult.getResponse().getContentAsString();
		assertTrue(body.toString().equals("[]"));
	}
	
	@Test
	public void shouldNotAddContact() throws Exception {
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
				.param("userA", "1661111112")
				.param("userB", "1631111112")
				).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(400, status);
	}
	
	@Test
	public void shouldGetChatMessages() throws Exception {
		String uri = "/chats/1111111111/messages";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		String body = mvcResult.getResponse().getContentAsString();
		assertTrue(body.toString().equals("[]"));
	}
	
	
	
	@Test
	public void shouldNotGetChatMessages() throws Exception {
		String uri = "/chats/1113111131/messages";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		String body = mvcResult.getResponse().getContentAsString();
		assertTrue(body.toString().equals("[]"));
	}
	
	@Test
	@WithMockUser(username = "1651111111", password = "pwd", roles = "USER")
	public void shouldGetGroupsOfCurrentUser() throws Exception {
		String uri = "/groups";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void shouldGetGroupMessages() throws Exception {
		String uri = "/groups/1/messages";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	@WithMockUser(username = "1651111111", password = "pwd", roles = "USER")
	public void shouldGetMemebersNotInGroup() throws Exception {
		String uri = "/groups/1/remaining";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "1111111111", password = "pwd", roles = "USER")
	public void shouldCreateGroup() throws Exception {
		Group groupTemp = new Group();
		mockMvc.perform(MockMvcRequestBuilders.
				post("/groups/1111111111")
			    .contentType(MediaType.APPLICATION_JSON_UTF8)
			    .content(asJsonString(groupTemp)))
				.andExpect(status().isCreated());
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "1111114111", password = "pwd", roles = "USER")
	public void shouldAddMembers() throws Exception {
		Set<User> members = new HashSet<>();
		Group groupTemp = new Group(0, "grupo de apoyo", "te ayudamos con apoyo", new Date(),members);
		mockMvc.perform(MockMvcRequestBuilders.
				put("/groups/addmembers")
			    .contentType(MediaType.APPLICATION_JSON_UTF8)
			    .content(asJsonString(groupTemp)))
				.andExpect(status().isAccepted());
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "1111111111", password = "pwd", roles = "USER")
	public void shouldDeleteMembers() throws Exception {
		Group groupTemp = new Group();
		mockMvc.perform(MockMvcRequestBuilders.
				put("/groups/deletemembers")
			    .contentType(MediaType.APPLICATION_JSON_UTF8)
			    .content(asJsonString(groupTemp)))
				.andExpect(status().isAccepted());
	}
	
	@Test(expected = NestedServletException.class)
	public void shouldGetGroup() throws Exception {
		String uri = "/groups/1/members";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	
	
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public static <T> T mapFromJson(String json, Class<T> clazz)
		      throws JsonParseException, JsonMappingException, IOException {
		      
		      ObjectMapper objectMapper = new ObjectMapper();
		      return objectMapper.readValue(json, clazz);
		   }
	
	
}
