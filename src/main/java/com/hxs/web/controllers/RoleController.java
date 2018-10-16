package com.hxs.web.controllers;

import com.hxs.data.projections.details.RoleDetails;
import com.hxs.data.projections.summaries.RoleSummary;
import com.hxs.data.projections.summaries.UserSummary;
import com.hxs.web.model.request.PermissionRequest;
import com.hxs.web.model.request.RoleRequest;
import com.hxs.web.model.request.UserRequest;
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
public interface RoleController {

    PagedResources<Resource<RoleDetails>> getPageOfRoles(Pageable pageable, PagedResourcesAssembler<RoleDetails> assembler);

    RoleSummary createRole(String roleName);

    void deleteRole(Long id);

    RoleSummary updateRole(Long id, RoleRequest roleRequest);

    RoleDetails getRoleDetails(Long id);

    RoleSummary addPermissions(Long id, List<PermissionRequest> permissions);

    RoleSummary addPermission(Long id, Long permId);

    RoleSummary removePermission(Long id, Long permId);

    RoleSummary addUser(Long id, Long userId);

    RoleSummary addUser(Long id, List<UserRequest> users);

    RoleSummary removeUser(Long id, Long userId);

    List<UserSummary> getRoleUsers(Long id);
}
