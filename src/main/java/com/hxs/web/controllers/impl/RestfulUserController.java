package com.hxs.web.controllers.impl;

import com.hxs.data.models.User;
import com.hxs.data.projections.summaries.PermissionSummary;
import com.hxs.data.projections.summaries.RoleSummary;
import com.hxs.data.projections.details.UserDetails;
import com.hxs.service.exceptions.EntityNotFoundException;
import com.hxs.web.controllers.UserController;
import com.hxs.web.model.reponse.ApiResponseMessage;
import com.hxs.web.model.request.*;
import com.hxs.web.utils.ProjectionUtils;
import com.hxs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *  Users resource controller.
 *
 * @author HSteidel
 */
@RestController()
@RequestMapping("/users")
public class RestfulUserController implements UserController {

    private final UserService userService;

    private final ProjectionUtils projectionUtils;

    @Autowired
    public RestfulUserController(UserService userService, ProjectionUtils projectionUtils) {
        this.userService = userService;
        this.projectionUtils = projectionUtils;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDetails createUser(@Valid @RequestBody UserCreateRequest userCreateRequest){
        User user = userService.createUser(userCreateRequest);
        return toUserDetails(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails getUserDetails(@PathVariable Long id){
        User user = userService.getOrThrowUserNotFound(id);
        return toUserDetails(user);
    }


    @GetMapping("/username/{username:.+}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails getUserDetails(@PathVariable String username){
        User user = userService.getUserOption(username).getOrElseThrow(
                                    () -> new EntityNotFoundException(User.class, username));
        return toUserDetails(user);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResources<Resource<UserDetails>> getPageOfUsers(Pageable pageable, PagedResourcesAssembler<UserDetails> assembler){
        Page<User> page = userService.getUserSummaryPage(pageable);
        return projectionUtils.toProjectionPageResource(UserDetails.class, page, pageable, assembler);
    }


    @GetMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.OK)
    public List<RoleSummary> getRolesAssignedToUser(@PathVariable Long id){
        return projectionUtils.toProjectionList(RoleSummary.class, userService.getUserRoles(id));
    }


    @PutMapping("/{id}/roles/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails updateUserRoles(@PathVariable Long id, @PathVariable Long roleId){
        User updated = userService.updateUserRole(id, roleId);
        return toUserDetails(updated);
    }


    @PutMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails updateUserRoles(@PathVariable Long id, @Valid @RequestBody List<RoleRequest> roleList){
        User updated = userService.updateUserRoles(id, roleList);
        return toUserDetails(updated);
    }

    @PutMapping("/{id}/permissions/{permId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails updateUserPermissions(@PathVariable Long id,@PathVariable Long permId){
        return toUserDetails(userService.updateUserPermission(id, permId));
    }


    @PutMapping("/{id}/permissions")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails updateUserPermissions(@PathVariable Long id, @Valid @RequestBody List<PermissionRequest> permissionsList){
        return toUserDetails(userService.updateUserPermissions(id, permissionsList)) ;
    }

    @GetMapping("/{id}/permissions")
    @ResponseStatus(HttpStatus.OK)
    public List<PermissionSummary> getAllPermissionsAssignedToUser(@PathVariable Long id){
        return projectionUtils.toProjectionList(PermissionSummary.class, userService.getUserPermissions(id));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails updateUserProperties(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest){
        return toUserDetails(userService.updateUser(id, userRequest));
    }

    @PutMapping("/passwords")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseMessage updateUserPassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest){
        userService.changePassword(passwordChangeRequest);
        return new ApiResponseMessage("Password change success.");
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    private UserDetails toUserDetails(User user) {
        return projectionUtils.toProjection(UserDetails.class, user);
    }
}
