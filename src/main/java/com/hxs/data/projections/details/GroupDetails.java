package com.hxs.data.projections.details;

import com.hxs.data.projections.summaries.UserSummary;

import java.util.Set;

/**
 * @author hsteidel
 */
public interface GroupDetails {

    Long getId();

    String getName();

    String getDescription();

    Set<UserSummary> getUsers();
}
