package com.noobprogrammer.splito.dto;


import java.util.Set;

public record GroupDTO(
    String name, 
    String adminUsername,
    Set<String> members
) {}
