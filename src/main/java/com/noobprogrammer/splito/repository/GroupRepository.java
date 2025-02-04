package com.noobprogrammer.splito.repository;

import com.noobprogrammer.splito.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);
    List<Group> findByMembersUsername(String username);
}
