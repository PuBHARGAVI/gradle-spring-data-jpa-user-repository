package com.hxs.data.projections.details;

import com.hxs.data.projections.summaries.PermissionSummary;
import com.hxs.data.projections.summaries.UserSummary;

import java.util.Set;

/**
 * @author hsteidel
 */
public interface RoleDetails {

    Long getId();

    String getName();

    Set<UserSummary> getUsers();

    Set<PermissionSummary> getPermissions();
}
