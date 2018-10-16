package com.hxs.web.controllers.impl;

import com.hxs.data.models.Group;
import com.hxs.data.models.User;
import com.hxs.data.projections.details.GroupDetails;
import com.hxs.data.projections.summaries.GroupSummary;
import com.hxs.data.projections.summaries.UserSummary;
import com.hxs.web.controllers.GroupController;
import com.hxs.web.model.request.GroupRequest;
import com.hxs.web.model.request.UserRequest;
import com.hxs.web.utils.ProjectionUtils;
import com.hxs.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author hsteidel
 */
@RestController
@RequestMapping("/groups")
public class RestfulControllerImpl implements GroupController {

    private final GroupService groupService;

    private final ProjectionUtils projectionUtils;

    @Autowired
    public RestfulControllerImpl(GroupService groupService, ProjectionUtils projectionUtils) {
        this.groupService = groupService;
        this.projectionUtils = projectionUtils;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResources<Resource<GroupSummary>> getPageOfGroups(Pageable pageable, PagedResourcesAssembler<GroupSummary> assembler){
        Page<Group> page = groupService.getGroupSummaryPage(pageable);
        return projectionUtils.toProjectionPageResource(GroupSummary.class, page, pageable, assembler);
    }

    @Override
    @GetMapping("{id}/users")
    @ResponseStatus(HttpStatus.OK)
    public PagedResources<Resource<UserSummary>> getPageOfGroupUsers(@PathVariable Long id, Pageable pageable, PagedResourcesAssembler<UserSummary> assembler){
        Page<User> page = groupService.getPageOfGroupUsers(id, pageable);
        return projectionUtils.toProjectionPageResource(UserSummary.class, page, pageable, assembler);
    }


    @Override
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public GroupDetails getGroupDetails(@PathVariable Long id){
        return toGroupDetails(groupService.getGroupOrThrowNotFound(id));
    }

    @Override
    @GetMapping("/name/{groupName}")
    @ResponseStatus(HttpStatus.OK)
    public GroupDetails getGroupDetailsByName(@PathVariable String groupName){
        return toGroupDetails(groupService.getGroupByNameOrThrowNotFound(groupName));
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GroupDetails updateGroupDetails(@PathVariable Long id, @Valid @RequestBody GroupRequest groupRequest){
        groupRequest.setId(id);
        return toGroupDetails(groupService.updateGroupDetails(groupRequest));
    }

    @Override
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDetails createGroup(@Valid @RequestBody GroupRequest groupRequest){
        return toGroupDetails(groupService.createGroup(groupRequest));
    }

    @Override
    @PutMapping("/{id}/users")
    @ResponseStatus(HttpStatus.OK)
    public GroupDetails addUsersToGroup(@PathVariable Long id, @Valid @RequestBody List<UserRequest> users){
        return toGroupDetails(groupService.addUsersToGroup(id, users));
    }

    @Override
    @PutMapping("/{id}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public GroupDetails addUserToGroup(@PathVariable Long id, @PathVariable Long userId){
        return toGroupDetails(groupService.addUserToGroup(id, userId));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(id);
    }

    private GroupDetails toGroupDetails(Group group){
        return projectionUtils.toProjection(GroupDetails.class, group);
    }
}
