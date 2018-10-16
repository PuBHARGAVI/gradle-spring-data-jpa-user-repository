package com.hxs.data.repositories;

import com.hxs.data.models.Group;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author HSteidel
 */
@Repository
@Profile("rdbms")
public interface GroupRepository extends PagingAndSortingRepository<Group, Long>{

    int countDistinctByNameAndIdNot(String name, Long id);

    @Query(nativeQuery = true, value = "select * from groups where name = ?1")
    Group findByName(String name);
}
