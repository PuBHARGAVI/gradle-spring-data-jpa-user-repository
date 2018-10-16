package com.hxs.web.controllers.impl;

import com.hxs.data.models.User;
import com.hxs.service.UserService;
import com.hxs.web.controllers.AuthenticationController;
import com.hxs.web.model.request.AuthenticationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author HSteidel
 */
@RestController
@RequestMapping("/authentication")
public class RestfulAuthenticationController implements AuthenticationController {

    private final UserService userService;

    public RestfulAuthenticationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Authenticates a user given a simple JSON { "username" : "", "password" : ""}
     *
     * @param authenticationRequest username/password combination
     * @return User object
     */
    @Override
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public User authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        return userService.authenticateUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    }
}
