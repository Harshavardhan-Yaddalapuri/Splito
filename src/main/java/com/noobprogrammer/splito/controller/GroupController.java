package com.noobprogrammer.splito.controller;

import com.noobprogrammer.splito.dto.CreateGroupRequest;
import com.noobprogrammer.splito.dto.GroupDTO;
import com.noobprogrammer.splito.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing expense groups.
 * Provides endpoints for creating, retrieving, updating, and deleting groups,
 * as well as managing group members.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    /**
     * Retrieves all groups that the authenticated user is a member of.
     *
     * @param userDetails the authenticated user's details
     * @return list of groups the user belongs to
     */
    @GetMapping
    public ResponseEntity<List<GroupDTO>> getUserGroups(
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("REST request to get groups for user: {}", userDetails.getUsername());
        return ResponseEntity.ok(groupService.getUserGroups(userDetails.getUsername()));
    }

    /**
     * Creates a new expense group with the authenticated user as admin.
     *
     * @param userDetails the authenticated user's details
     * @param request the group creation request containing group details
     * @return the created group
     */
    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateGroupRequest request) {
        log.debug("REST request to create group: {} for user: {}", request.getName(), userDetails.getUsername());
        return ResponseEntity.ok(groupService.createGroup(userDetails.getUsername(), request));
    }

    /**
     * Adds new members to an existing group.
     * Only the group admin can add members.
     *
     * @param userDetails the authenticated user's details (must be group admin)
     * @param groupId the ID of the group to add members to
     * @param memberUsernames list of usernames to add to the group
     * @return the updated group
     */
    @PostMapping("/{groupId}/members")
    public ResponseEntity<GroupDTO> addMembers(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long groupId,
            @RequestBody List<String> memberUsernames) {
        log.debug("REST request to add members: {} to group: {} by user: {}", 
                memberUsernames, groupId, userDetails.getUsername());
        return ResponseEntity.ok(groupService.addMembers(userDetails.getUsername(), groupId, memberUsernames));
    }

    /**
     * Removes members from an existing group.
     * Only the group admin can remove members.
     *
     * @param userDetails the authenticated user's details (must be group admin)
     * @param groupId the ID of the group to remove members from
     * @param memberUsernames list of usernames to remove from the group
     * @return void
     */
    @DeleteMapping("/{groupId}/members")
    public ResponseEntity<Void> removeMembers(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long groupId,
            @RequestBody List<String> memberUsernames) {
        log.debug("REST request to remove members: {} from group: {} by user: {}", 
                memberUsernames, groupId, userDetails.getUsername());
        groupService.removeMembers(userDetails.getUsername(), groupId, memberUsernames);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes an existing group.
     * Only the group admin can delete the group.
     * A group cannot be deleted if it has any expenses.
     *
     * @param userDetails the authenticated user's details (must be group admin)
     * @param groupId the ID of the group to delete
     * @return void
     */
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long groupId) {
        log.debug("REST request to delete group: {} by user: {}", groupId, userDetails.getUsername());
        groupService.deleteGroup(userDetails.getUsername(), groupId);
        return ResponseEntity.ok().build();
    }
}
