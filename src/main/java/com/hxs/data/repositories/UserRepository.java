package com.hxs.data.repositories;

import com.hxs.data.models.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author HSteidel
 */
@Repository
@Profile("rdbms")
public interface UserRepository extends PagingAndSortingRepository<User, Long>{

    User findByUsername(String username);

    Set<User> findByIdIn(List<Long> userIds);

    int countDistinctByUsernameAndIdNot(String name, Long id);

    @Query("select u from User u join u.groups g where g.id = :groupId")
    Page<User> findPageOfUsersInGroup(Long groupId, Pageable pageable);
}

