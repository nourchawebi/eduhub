package com.esprit.cloudcraft.entities.userEntities;

import java.util.Arrays;
import java.util.List;

public enum ClassType {
    FIRSTYEAR,
    SECONDYEAR,
    THIRDYEAR,
    FOURTHYEAR,
    FIFTHYEAR;
    public static List<ClassType> getAllClassTypes() {
        return Arrays.asList(values());
    }
}
