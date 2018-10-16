package com.hxs.service.rdbms;


import com.hxs.data.models.Permission;
import com.hxs.data.models.Role;
import com.hxs.data.models.User;
import com.hxs.data.repositories.PermissionsRepository;
import com.hxs.data.repositories.RoleRepository;
import com.hxs.data.repositories.UserRepository;
import com.hxs.service.RoleService;
import com.hxs.service.exceptions.EntityNotFoundException;
import com.hxs.service.exceptions.ResourceConflictException;
import com.hxs.web.model.request.PermissionRequest;
import com.hxs.web.model.request.RoleRequest;
import com.hxs.web.model.request.UserRequest;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author hsteidel
 */
@Service
@Transactional
@Profile("rdbms")
public class RelationalRoleService implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionsRepository permissionsRepository;

    private final UserRepository userRepository;

    @Autowired
    public RelationalRoleService(RoleRepository roleRepository, PermissionsRepository permissionsRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.permissionsRepository = permissionsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Role createRole(String roleName) {
        if (roleRepository.findByName(roleName) != null) {
            throw new ResourceConflictException("Role already exists.");
        }
        Role role = new Role();
        role.setName(roleName);
        role = roleRepository.save(role);
        return role;
    }


    @Override
    public Role getRoleOrThrowNotFound(Long roleId) {
        return Option.ofOptional(roleRepository.findById(roleId))
                .getOrElseThrow(() -> new EntityNotFoundException(Role.class, roleId));
    }

    @Override
    public Role updateRole(Long id, RoleRequest roleRequest) {
        Role roleToUpdate = getRoleOrThrowNotFound(id);

        if (roleRepository.countDistinctByNameAndIdNot(roleRequest.getName(), roleToUpdate.getId()) > 0) {
            throw new ResourceConflictException("Role name is already being used.");
        }

        roleToUpdate.setName(roleRequest.getName());
        roleRepository.save(roleToUpdate);

        return roleToUpdate;
    }

    @Override
    public void deleteRole(Long id) {
        Role roleToUpdate = getRoleOrThrowNotFound(id);
        if (roleToUpdate != null) {
            if (!roleToUpdate.getUsers().isEmpty()) {
                throw new IllegalStateException("Role is being used.");
            }
            roleRepository.deleteById(id);
        }
    }


    @Override
    public Page<Role> getPageOfRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }


    @Override
    public Role addPermissions(Long id, List<PermissionRequest> permissions) {
        Role role = getRoleOrThrowNotFound(id);

        for (PermissionRequest pj : permissions) {
            role = addPermission(id, pj.getId());
        }

        return role;
    }

    @Override
    public Role addPermission(Long roleId, Long permId) {
        Role roleToUpdate = getRoleOrThrowNotFound(roleId);

        Permission permission = Option.ofOptional(permissionsRepository.findById(permId))
                .getOrElseThrow(() -> new EntityNotFoundException(Permission.class, permId));
        roleToUpdate.getPermissions().add(permission);
        roleRepository.save(roleToUpdate);

        return roleToUpdate;
    }

    @Override
    public Role removePermission(Long roleId, Long permId) {
        Role roleToUpdate = getRoleOrThrowNotFound(roleId);

        Permission permission = Option.ofOptional(permissionsRepository.findById(permId))
                .getOrElseThrow(() -> new EntityNotFoundException(Permission.class, permId));
        roleToUpdate.getPermissions().remove(permission);
        roleRepository.save(roleToUpdate);

        return roleToUpdate;
    }


    @Override
    public Role addUsersToRole(Long id, List<UserRequest> users) {
        Role role = getRoleOrThrowNotFound(id);

        for (UserRequest userRequest : users) {
            role = addUserToRole(id, userRequest.getId());
        }

        return role;
    }

    @Override
    public Role addUserToRole(Long roleId, Long userId) {
        Role foundRole = getRoleOrThrowNotFound(roleId);

        User user = Option.ofOptional(userRepository.findById(userId))
                .getOrElseThrow(() -> new EntityNotFoundException(User.class, userId));

        foundRole.getUsers().add(user);
        foundRole = roleRepository.save(foundRole);

        user.getRoles().add(foundRole);
        userRepository.save(user);

        return foundRole;
    }


    @Override
    public Role removeUserFromRole(Long roleId, Long userId) {
        Role foundRole = getRoleOrThrowNotFound(roleId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userId));

        foundRole.getUsers().remove(user);
        foundRole = roleRepository.save(foundRole);

        user.getRoles().remove(foundRole);
        userRepository.save(user);

        return foundRole;
    }


    @Override
    public Set<User> getRoleUsers(Long id) {
        Role foundRole = getRoleOrThrowNotFound(id);
        Set<User> userList = null;
        if (foundRole != null) {
            userList = foundRole.getUsers();
        }
        return userList;
    }
}
