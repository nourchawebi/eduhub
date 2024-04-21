package com.esprit.cloudcraft.entities.userEntities;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
@RequiredArgsConstructor
public enum RoleType {

        USER,
        ADMIN;
        public static List<RoleType> getAllRoleTypes() {
                return Arrays.asList(values());
        }

}
