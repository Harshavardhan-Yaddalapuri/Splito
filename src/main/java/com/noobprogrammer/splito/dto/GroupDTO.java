package com.noobprogrammer.splito.dto;

import com.noobprogrammer.splito.model.Expense;
import com.noobprogrammer.splito.model.User;

import java.util.Set;

public record GroupDTO(Long id, String name, Set<User> members, Set<Expense> expenses) {
}
