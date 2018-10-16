package com.hxs.service;

import com.hxs.data.models.Permission;
import com.hxs.data.models.Role;
import com.hxs.data.models.User;
import com.hxs.web.model.request.*;
import io.vavr.control.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * @author HSteidel
 */
public interface UserService {
    User createUser(UserCreateRequest userRequest);

    Option<User> getUserOption(Long id);

    Option<User> getUserOption(String username);

    Page<User> getUserSummaryPage(Pageable pageable);

    User getOrThrowUserNotFound(Long id);

    User getOrThrowUserNotFound(String username);

    Set<Role> getUserRoles(Long id);

    void changePassword(PasswordChangeRequest passwordChangeRequest);

    void deleteUser(Long id);

    User authenticateUser(String username, String password);

    User updateUserRole(Long userId, Long roleId);

    User updateUserRoles(Long id, List<RoleRequest> roleList);

    User updateUserPermission(Long userId, Long permId);

    User updateUserPermissions(Long id, List<PermissionRequest> permissionRequestList);

    Set<Permission> getPermissions(List<PermissionRequest> permissionRequestList);

    Set<Role> getRoles(List<RoleRequest> roleRequestList);

    User updateUser(Long id, UserRequest userRequest);

    List<Permission> getUserPermissions(Long id);
}
