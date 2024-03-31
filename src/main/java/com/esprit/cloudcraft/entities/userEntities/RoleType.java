package com.esprit.cloudcraft.entities.userEntities;

import java.util.Arrays;
import java.util.List;

public enum RoleType {

        USER,
        ADMIN;
        public static List<RoleType> getAllRoleTypes() {
                return Arrays.asList(values());
        }

}
