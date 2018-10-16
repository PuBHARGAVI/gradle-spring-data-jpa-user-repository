package com.hxs.service;

import com.hxs.data.models.Role;
import com.hxs.data.models.User;
import com.hxs.web.model.request.PermissionRequest;
import com.hxs.web.model.request.RoleRequest;
import com.hxs.web.model.request.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * @author HSteidel
 */
public interface RoleService {
    Role createRole(String roleName);

    Role getRoleOrThrowNotFound(Long roleId);

    Role updateRole(Long id, RoleRequest roleRequest);

    void deleteRole(Long id);

    Page<Role> getPageOfRoles(Pageable pageable);

    Role addPermissions(Long id, List<PermissionRequest> permissions);

    Role addPermission(Long roleId, Long permId);

    Role removePermission(Long roleId, Long permId);

    Role addUsersToRole(Long id, List<UserRequest> users);

    Role addUserToRole(Long roleId, Long userId);

    Role removeUserFromRole(Long roleId, Long userId);

    Set<User> getRoleUsers(Long id);
}
