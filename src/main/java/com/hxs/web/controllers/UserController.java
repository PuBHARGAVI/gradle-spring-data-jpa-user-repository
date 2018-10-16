package com.hxs.web.controllers;

import com.hxs.data.projections.details.UserDetails;
import com.hxs.data.projections.summaries.PermissionSummary;
import com.hxs.data.projections.summaries.RoleSummary;
import com.hxs.web.model.reponse.ApiResponseMessage;
import com.hxs.web.model.request.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

import java.util.List;

/**
 *  TODO: Swagger annotate
 *
 * @author HSteidel
 */
public interface UserController {

    UserDetails createUser(UserCreateRequest userCreateRequest);

    UserDetails getUserDetails(Long id);

    UserDetails getUserDetails(String username);

    PagedResources<Resource<UserDetails>> getPageOfUsers(Pageable pageable, PagedResourcesAssembler<UserDetails> assembler);

    List<RoleSummary> getRolesAssignedToUser(Long id);

    UserDetails updateUserRoles(Long id, Long roleId);

    UserDetails updateUserRoles(Long id, List<RoleRequest> roleList);

    UserDetails updateUserPermissions(Long id, Long permId);

    UserDetails updateUserPermissions(Long id, List<PermissionRequest> permissionsList);

    List<PermissionSummary> getAllPermissionsAssignedToUser(Long id);

    UserDetails updateUserProperties(Long id, UserRequest userRequest);

    ApiResponseMessage updateUserPassword(PasswordChangeRequest passwordChangeRequest);

    void deleteUser(Long id);
}
