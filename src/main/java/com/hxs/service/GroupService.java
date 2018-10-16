package com.hxs.service;

import com.hxs.data.models.Group;
import com.hxs.data.models.User;
import com.hxs.web.model.request.GroupRequest;
import com.hxs.web.model.request.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * @author HSteidel
 */
public interface GroupService {
    Page<Group> getGroupSummaryPage(Pageable pageable);

    Page<User> getPageOfGroupUsers(Long id, Pageable pageable);

    Group getByID(Long id);

    Group getGroupByNameOrThrowNotFound(String name);

    Group getGroupOrThrowNotFound(Long id);

    Group updateGroupDetails(GroupRequest groupRequest);

    Group createGroup(GroupRequest groupRequest);

    void deleteGroup(Long id);

    Group addUsersToGroup(Long id, List<UserRequest> users);

    Set<User> getUserEntities(List<UserRequest> userRequestList);

    Group addUserToGroup(Long id, Long userId);
}
