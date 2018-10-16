package com.hxs.web.controllers.impl;

import com.hxs.data.models.Role;
import com.hxs.data.models.User;
import com.hxs.data.projections.details.RoleDetails;
import com.hxs.data.projections.summaries.RoleSummary;
import com.hxs.data.projections.summaries.UserSummary;
import com.hxs.service.RoleService;
import com.hxs.web.controllers.RoleController;
import com.hxs.web.model.request.PermissionRequest;
import com.hxs.web.model.request.RoleRequest;
import com.hxs.web.model.request.UserRequest;
import com.hxs.web.utils.ProjectionUtils;
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
import java.util.Set;

@RestController()
@RequestMapping("/roles")
public class RestfulRoleController implements RoleController {

    private final RoleService roleService;
    private final ProjectionUtils projectionUtils;

    @Autowired
    public RestfulRoleController(RoleService roleService, ProjectionUtils projectionUtils) {
        this.roleService = roleService;
        this.projectionUtils = projectionUtils;
    }

    @Override
    @GetMapping()
    @ResponseStatus(code = HttpStatus.OK)
    public PagedResources<Resource<RoleDetails>> getPageOfRoles(Pageable pageable, PagedResourcesAssembler<RoleDetails> assembler) {
        Page<Role> page = roleService.getPageOfRoles(pageable);
        return projectionUtils.toProjectionPageResource(RoleDetails.class, page, pageable, assembler);
    }

    @Override
    @PostMapping("/{roleName}")
    @ResponseStatus(HttpStatus.CREATED)
    public RoleSummary createRole(@PathVariable String roleName) {
        Role role = roleService.createRole(roleName.toUpperCase());
        return projectionUtils.toProjection(RoleSummary.class, role);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }


    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleSummary updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest roleRequest) {
        Role updateRole = roleService.updateRole(id, roleRequest);
        return projectionUtils.toProjection(RoleSummary.class, updateRole);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDetails getRoleDetails(@PathVariable Long id) {
        Role role = roleService.getRoleOrThrowNotFound(id);
        return projectionUtils.toProjection(RoleDetails.class, role);
    }

    @Override
    @PutMapping("/{id}/permission")
    @ResponseStatus(HttpStatus.OK)
    public RoleSummary addPermissions(@PathVariable Long id, @Valid @RequestBody List<PermissionRequest> permissions) {
        Role updateRole = roleService.addPermissions(id, permissions);
        return projectionUtils.toProjection(RoleSummary.class, updateRole);
    }

    @Override
    @PutMapping("/{id}/permission/{permId}")
    @ResponseStatus(HttpStatus.OK)
    public RoleSummary addPermission(@PathVariable Long id, @PathVariable Long permId) {
        Role updateRole = roleService.addPermission(id, permId);
        return projectionUtils.toProjection(RoleSummary.class, updateRole);
    }

    @Override
    @DeleteMapping("/{id}/permission/{permId}")
    @ResponseStatus(HttpStatus.OK)
    public RoleSummary removePermission(@PathVariable Long id, @PathVariable Long permId) {
        Role updateRole = roleService.removePermission(id, permId);
        return projectionUtils.toProjection(RoleSummary.class, updateRole);
    }

    @Override
    @PutMapping("/{id}/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public RoleSummary addUser(@PathVariable Long id, @PathVariable Long userId) {
        Role role = roleService.addUserToRole(id, userId);
        return projectionUtils.toProjection(RoleSummary.class, role);
    }

    @Override
    @PutMapping("/{id}/user")
    @ResponseStatus(HttpStatus.OK)
    public RoleSummary addUser(@PathVariable Long id, @Valid @RequestBody List<UserRequest> users) {
        Role role = roleService.addUsersToRole(id, users);
        return projectionUtils.toProjection(RoleSummary.class, role);
    }

    //Remove the user from the role
    @Override
    @DeleteMapping("/{id}/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public RoleSummary removeUser(@PathVariable Long id, @PathVariable Long userId) {
        Role role = roleService.removeUserFromRole(id, userId);
        return projectionUtils.toProjection(RoleSummary.class, role);
    }

    @Override
    @GetMapping("/{id}/users")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserSummary> getRoleUsers(@PathVariable Long id) {
        Set<User> userList = roleService.getRoleUsers(id);
        return projectionUtils.toProjectionList(UserSummary.class, userList);
    }
}
