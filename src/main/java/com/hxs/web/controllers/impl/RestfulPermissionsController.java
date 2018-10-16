package com.hxs.web.controllers.impl;

import com.hxs.data.models.Permission;
import com.hxs.data.projections.details.PermissionDetails;
import com.hxs.web.controllers.PermissionsController;
import com.hxs.web.utils.ProjectionUtils;
import com.hxs.service.PermissionsService;
import com.hxs.web.model.request.PermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hsteidel
 */
@RestController
@RequestMapping("/permissions")
public class RestfulPermissionsController implements PermissionsController {

    private final PermissionsService permissionsService;

    private final ProjectionUtils projectionUtils;

    @Autowired
    public RestfulPermissionsController(PermissionsService permissionsService, ProjectionUtils projectionUtils) {
        this.permissionsService = permissionsService;
        this.projectionUtils = projectionUtils;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResources<Resource<PermissionDetails>> getPageOfUsers(Pageable pageable, PagedResourcesAssembler<PermissionDetails> assembler){
        Page<Permission> page = permissionsService.getPermissionSummaryPage(pageable);
        return projectionUtils.toProjectionPageResource(PermissionDetails.class, page, pageable, assembler);
    }


    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PermissionDetails getPermissionSummary(@PathVariable Long id){
        return toPermissionDetails(permissionsService.getPermOrThrowNotFound(id));
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PermissionDetails updatePermission(@PathVariable Long id, @Valid @RequestBody PermissionRequest permissionRequest){
        permissionRequest.setId(id);
        return toPermissionDetails(permissionsService.updatePermission(permissionRequest));
    }

    @Override
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionDetails createPermission(@Valid @RequestBody PermissionRequest permissionRequest){
        return toPermissionDetails(permissionsService.createPermission(permissionRequest));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePermission(@PathVariable Long id){
        permissionsService.delete(id);
    }


    private PermissionDetails toPermissionDetails(Permission permission){
        return projectionUtils.toProjection(PermissionDetails.class, permission);
    }
}
