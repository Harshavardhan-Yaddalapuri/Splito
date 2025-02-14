package com.noobprogrammer.splito.service;

import com.noobprogrammer.splito.dto.CreateGroupRequest;
import com.noobprogrammer.splito.dto.GroupDTO;

import java.util.List;

public interface GroupService {

    /**
     * Get all groups for a user
     */
    List<GroupDTO> getUserGroups(String username);

    /**
     * Create a new group
     */
    GroupDTO createGroup(String username, CreateGroupRequest request);

    /**
     * Add members to a group
     */
    GroupDTO addMembers(String username, Long groupId, List<String> memberUsernames);

    /**
     * Remove members from a group
     */
    void removeMembers(String username, Long groupId, List<String> memberUsernames);

    /**
     * Delete a group
     */
    void deleteGroup(String username, Long groupId);
}
