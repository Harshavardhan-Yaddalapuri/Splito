package com.noobprogrammer.splito.service;

import com.noobprogrammer.splito.dto.GroupDTO;
import com.noobprogrammer.splito.model.User;

import java.util.List;
import java.util.Set;

public class GroupServiceImpl implements GroupService{


    @Override
    public List<GroupDTO> viewAllGroups(String username) {
        /* TODO:
        * 1. check if the user already exists or not
        * 2. If yes, return all the groups that user is part of
        * 2.1 If no, throw an error
        * */

        return null;
    }

    @Override
    public void createGroup(String groupName, String username) { //TODO: Decide on which fields to use for group creation user_id or username

        /* TODO: Implement this method
         * 1. Check if the username passed is valid user or not?
         * 2. User status ?
         * 2.1 If the user is valid, then create group with the provided group name
         * 2.2 If the user is not valid, then throw an exception
         * 3. save the user as member into the group
         * 3.1 Save the group object into the database
         * 4. send out a proper response status and meaningful message
        * */

    }

    @Override
    public void addUserToGroup(String groupName, String username, Set<User> members ) {

        /*
        * TODO: Implement this method
        *  1. Check if the group exists or not?
        *  2. If yes, then fetch the group_id's in case if there are multiple groups with same name
        *  3. check if the user id present in the group? add the users to the selected group
        *  4. Save the details into the database
        * */

    }

    @Override
    public void removeUserFromGroup(String groupName, String userToBeRemoved) {

        /*
         * TODO: Implement this method
         *  1. Check if the group exists or not? and check if the userToBeRemoved exists or not?
         *  3. IF user exists then, remove or else throw an error.
         *
         * */

    }

    @Override
    public void deleteGroup(String groupName) {



    }


}
