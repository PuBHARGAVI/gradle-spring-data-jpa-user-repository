package com.hxs.web.controllers;

import com.hxs.data.models.User;
import com.hxs.web.model.request.AuthenticationRequest;

/**
 * TODO: Swagger annotate
 *
 * @author HSteidel
 */
public interface AuthenticationController {

    User authenticateUser(AuthenticationRequest authenticationRequest);

}
