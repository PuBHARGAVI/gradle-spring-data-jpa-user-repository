package com.hxs.data.projections.details;

import com.hxs.data.projections.summaries.GroupSummary;
import com.hxs.data.projections.summaries.PermissionSummary;
import com.hxs.data.projections.summaries.RoleSummary;

import java.util.List;

/**
 *
 * @author hsteidel
 */
public interface UserDetails {

    Long getId();

    String getUsername();

    List<RoleSummary> getRoles();

    List<PermissionSummary> getPermissions();

    List<GroupSummary> getGroups();
}
