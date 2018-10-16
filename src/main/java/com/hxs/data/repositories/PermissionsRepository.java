package com.hxs.data.repositories;

import com.hxs.data.models.Permission;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author HSteidel
 */
@Repository
@Profile("rdbms")
public interface PermissionsRepository extends PagingAndSortingRepository<Permission, Long>{

    Set<Permission> findByIdIn(List<Long> permissionIds);

    Permission findByName(String name);

    int countDistinctByNameAndIdNot(String name, Long id);

}
