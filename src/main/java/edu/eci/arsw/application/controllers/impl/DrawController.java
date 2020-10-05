package edu.eci.arsw.application.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.application.controllers.BaseController;
import edu.eci.arsw.application.services.DrawChatService;

@RestController
@RequestMapping(path = "/draw")
public class DrawController implements BaseController {

    @Autowired
    private DrawChatService drawChatService;

}
