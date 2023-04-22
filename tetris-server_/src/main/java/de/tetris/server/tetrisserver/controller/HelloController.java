package de.tetris.server.tetrisserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST-API controller for checking server availability
 */
@RestController
@RequestMapping("/tetris")
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
