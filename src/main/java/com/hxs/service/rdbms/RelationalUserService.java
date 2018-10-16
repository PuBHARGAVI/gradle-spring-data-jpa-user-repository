package com.hxs.service.rdbms;

import com.hxs.data.models.Permission;
import com.hxs.data.models.Role;
import com.hxs.data.models.User;
import com.hxs.data.repositories.PermissionsRepository;
import com.hxs.data.repositories.RoleRepository;
import com.hxs.data.repositories.UserRepository;
import com.hxs.service.UserService;
import com.hxs.service.aop.Notifies;
import com.hxs.service.exceptions.EntityNotFoundException;
import com.hxs.service.exceptions.ResourceConflictException;
import com.hxs.service.exceptions.UnauthorizedActionException;
import com.hxs.web.model.request.*;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hsteidel
 */
@Service
@Transactional
@Profile("rdbms")
public class RelationalUserService implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PermissionsRepository permissionsRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public RelationalUserService(UserRepository userRepository, RoleRepository roleRepository, PermissionsRepository permissionsRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionsRepository = permissionsRepository;
    }

    @Override
    @Notifies
    public User createUser(UserCreateRequest userRequest) {

        if (userRepository.findByUsername(userRequest.getUsername()) != null) {
            throw new ResourceConflictException(userRequest.getUsername() + " already exists.");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Option<User> getUserOption(Long id) {
        return Option.ofOptional(userRepository.findById(id));
    }

    @Override
    public Option<User> getUserOption(String username) {
        return Option.of(userRepository.findByUsername(username));
    }


    @Override
    public Page<User> getUserSummaryPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getOrThrowUserNotFound(Long id) {
        return getUserOption(id).getOrElseThrow(
                () -> new EntityNotFoundException(User.class, String.valueOf(id)));
    }

    @Override
    public User getOrThrowUserNotFound(String username) {
        return getUserOption(username).getOrElseThrow(
                () -> new EntityNotFoundException(User.class, username));
    }

    @Override
    public Set<Role> getUserRoles(Long id) {
        User user = getOrThrowUserNotFound(id);
        return user.getRoles();
    }


    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        User user = getOrThrowUserNotFound(passwordChangeRequest.getUsername());
        if(doesPasswordMatch(passwordChangeRequest.getCurrentPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new UnauthorizedActionException("Password change failed due to invalid credentials.");
        }
    }


    @Override
    public void deleteUser(Long id) {
        Option<User> userOption = getUserOption(id);
        if (userOption.isDefined()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public User authenticateUser(String username, String password) {
        User user = getOrThrowUserNotFound(username);
        if(!doesPasswordMatch(password, user.getPassword())) {
            throw new UnauthorizedActionException("Invalid credentials.");
        }
        return user;
    }

    @Override
    public User updateUserRole(Long userId, Long roleId) {
        Role role = Option.ofOptional(roleRepository.findById(roleId))
                .getOrElseThrow(() -> new EntityNotFoundException(Permission.class, roleId));

        User user = getOrThrowUserNotFound(userId);

        user.getRoles().add(role);
        user = userRepository.save(user);

        role.getUsers().add(user);
        roleRepository.save(role);

        return user;
    }

    @Override
    public User updateUserRoles(Long id, List<RoleRequest> roleList) {
        User user = getOrThrowUserNotFound(id);
        user.setRoles(getRoles(roleList));
        return userRepository.save(user);
    }

    @Override
    public User updateUserPermission(Long userId, Long permId) {
        Permission permission = Option.ofOptional(permissionsRepository.findById(permId))
                .getOrElseThrow(() -> new EntityNotFoundException(Permission.class, permId));

        User user = getOrThrowUserNotFound(userId);
        user.getPermissions().add(permission);
        return userRepository.save(user);
    }

    @Override
    public User updateUserPermissions(Long id, List<PermissionRequest> permissionRequestList) {
        User user = getOrThrowUserNotFound(id);
        user.setPermissions(getPermissions(permissionRequestList));
        return userRepository.save(user);
    }

    @Override
    public Set<Permission> getPermissions(List<PermissionRequest> permissionRequestList) {
        return permissionsRepository.findByIdIn(permissionRequestList.stream()
                .map(PermissionRequest::getId)
                .collect(Collectors.toList()));
    }

    @Override
    public Set<Role> getRoles(List<RoleRequest> roleRequestList) {
        return roleRepository.findByIdIn(roleRequestList.stream()
                .map(RoleRequest::getId)
                .collect(Collectors.toList()));
    }

    @Override
    public User updateUser(Long id, UserRequest userRequest) {
        User user = getOrThrowUserNotFound(id);

        if (userRepository.countDistinctByUsernameAndIdNot(userRequest.getUsername(), user.getId()) > 0) {
            throw new ResourceConflictException("Username is already being used.");
        }

        user.setUsername(userRequest.getUsername());
        return userRepository.save(user);
    }

    @Override
    public List<Permission> getUserPermissions(Long id) {
        User user = getOrThrowUserNotFound(id);
        List<Permission> allPermissions = new ArrayList<>(user.getPermissions());
        user.getRoles().forEach(r -> allPermissions.addAll(r.getPermissions()));
        return allPermissions;
    }


    private boolean doesPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
