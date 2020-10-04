package edu.eci.arsw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import edu.eci.arsw.application.DrawChatApp;
import edu.eci.arsw.application.controllers.UserController;
import edu.eci.arsw.application.services.DrawChatService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DrawChatApp.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) 
@AutoConfigureMockMvc
public class DrawChatControllerTest {

	@Autowired
	private DrawChatService service;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void test(){
	}
}
