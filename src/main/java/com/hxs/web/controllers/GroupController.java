package com.hxs.web.controllers;

import com.hxs.data.projections.details.GroupDetails;
import com.hxs.data.projections.summaries.GroupSummary;
import com.hxs.data.projections.summaries.UserSummary;
import com.hxs.web.model.request.GroupRequest;
import com.hxs.web.model.request.UserRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO: Swagger annotate
 *
 * @author HSteidel
 */
public interface GroupController {

    PagedResources<Resource<GroupSummary>> getPageOfGroups(Pageable pageable, PagedResourcesAssembler<GroupSummary> assembler);

    PagedResources<Resource<UserSummary>> getPageOfGroupUsers(@PathVariable Long id, Pageable pageable, PagedResourcesAssembler<UserSummary> assembler);

    GroupDetails getGroupDetails(@PathVariable Long id);

    GroupDetails getGroupDetailsByName(@PathVariable String groupName);

    GroupDetails updateGroupDetails(@PathVariable Long id, @Valid @RequestBody GroupRequest groupRequest);

    GroupDetails createGroup(@Valid @RequestBody GroupRequest groupRequest);

    GroupDetails addUsersToGroup(@PathVariable Long id, @Valid @RequestBody List<UserRequest> users);

    GroupDetails addUserToGroup(@PathVariable Long id, @PathVariable Long userId);

    void deleteGroup(@PathVariable Long id);
}
