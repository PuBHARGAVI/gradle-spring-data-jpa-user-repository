package com.hxs.service;

import com.hxs.data.models.Permission;
import com.hxs.web.model.request.PermissionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author HSteidel
 */
public interface PermissionsService {
    Page<Permission> getPermissionSummaryPage(Pageable pageable);

    Permission getById(Long id);

    Permission getPermOrThrowNotFound(Long id);

    Permission updatePermission(PermissionRequest permissionRequest);

    Permission createPermission(PermissionRequest permissionRequest);

    void delete(Long id);
}
