	package edu.eci.arsw.application.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.arsw.application.controllers.impl.DrawController;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador Usuario
 */
@RestController
@RequestMapping("/users")
@Service
public class UserController {

	@Autowired
	private DrawChatService drawChatService;

	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getUsers() {
		try {
			drawChatService.getUsers();
			return new ResponseEntity<>(drawChatService.getUsers(), HttpStatus.ACCEPTED);
		} catch (Exception ex) {
			Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("HTTP 404 Not Found", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{telefono}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable long telefono) {
		try {
			drawChatService.getUser(telefono);
			return new ResponseEntity<>(drawChatService.getUser(telefono), HttpStatus.OK);
		} catch (Exception ex) {
			Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("HTTP 404 Not Found", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{telefono}/contacts", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getContactsByUser(@PathVariable String telefono) {
		long number = Long.parseLong(telefono);
		try {
			System.out.println(drawChatService.getContacts(number));
			return new ResponseEntity<>(drawChatService.getContacts(number), HttpStatus.OK);
		} catch (Exception ex) {
			Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("HTTP 404 Not Found", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody String user) {
		try {
			User nuevo = mapper.readValue(user, User.class);
			if (drawChatService.getCurrentUserSession().getTelefono() != nuevo.getTelefono()) {
				return new ResponseEntity<>("401 Unauthorized", HttpStatus.UNAUTHORIZED);
			}
			if (nuevo.getContraseña() != null) {
				nuevo.setContraseña(new BCryptPasswordEncoder().encode(nuevo.getContraseña()));
			}
			drawChatService.updateUser(nuevo);
		} catch (JsonProcessingException | AppException e) {
			Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, e);
			return new ResponseEntity<>("400 Bad Request", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("200 OK", HttpStatus.OK);
	}

	@GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCurrentUser() {
		try {
			System.out.println(drawChatService.getCurrentUserSession());
			return new ResponseEntity<>(
					mapper.writerWithDefaultPrettyPrinter().writeValueAsString(drawChatService.getCurrentUserSession()),
					HttpStatus.OK);
		} catch (JsonProcessingException | AppException e) {
			Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, e);
			return new ResponseEntity<>("500 Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
