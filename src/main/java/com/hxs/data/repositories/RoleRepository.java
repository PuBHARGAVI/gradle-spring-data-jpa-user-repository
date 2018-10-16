package com.hxs.data.repositories;

import com.hxs.data.models.Role;
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
public interface RoleRepository extends PagingAndSortingRepository<Role, Long>{

    Role findByName(String roleName);

    Set<Role> findByIdIn(List<Long> roleIdList);

    int countDistinctByNameAndIdNot(String name, Long id);

}
