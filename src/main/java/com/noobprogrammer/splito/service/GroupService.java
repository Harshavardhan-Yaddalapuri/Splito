package com.noobprogrammer.splito.service;

import com.noobprogrammer.splito.dto.GroupDTO;
import com.noobprogrammer.splito.model.User;

import java.util.List;
import java.util.Set;

public interface GroupService {

    List<GroupDTO> viewAllGroups(String username);

    void createGroup(String groupName, String username);

    void addUserToGroup(String groupName, String username, Set<User> members);

    void removeUserFromGroup(String groupName, String userToBeRemoved);

    void deleteGroup(String groupName);

}
