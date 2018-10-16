package com.hxs.web.controllers;

import com.hxs.data.projections.details.PermissionDetails;
import com.hxs.web.model.request.PermissionRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  TODO: Swagger annotate
 *
 * @author HSteidel
 */
public interface PermissionsController {

    PagedResources<Resource<PermissionDetails>> getPageOfUsers(Pageable pageable, PagedResourcesAssembler<PermissionDetails> assembler);

    PermissionDetails getPermissionSummary(Long id);

    PermissionDetails updatePermission(Long id, PermissionRequest permissionRequest);

    PermissionDetails createPermission(PermissionRequest permissionRequest);

    void deletePermission(Long id);
}
