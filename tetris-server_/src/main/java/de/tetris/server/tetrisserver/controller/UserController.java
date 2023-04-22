package de.tetris.server.tetrisserver.controller;

import de.tetris.server.tetrisserver.model.User;
import de.tetris.server.tetrisserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-API controller for user signIn
 */
@RestController
@RequestMapping("/tetris")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/add")
    public ResponseEntity<String> postUser(@RequestBody User user) {

        try {
            user = userService.save(user);
            return new ResponseEntity<>("SignUp succcesseeded", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("SignUp failed", HttpStatus.CONFLICT);
        }


    }
}
