package org.cloudcraft.coursemanagementservice.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class UtilFunctions {

    public static Date getCurrentDateSql(){
        return Date.valueOf(LocalDate.now());
    }
    public static  String generateRandomString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    public static String makeRandomFileName(String originalFileName){
        return originalFileName.split("\\.")[0]+generateRandomString()+"."+originalFileName.split("\\.")[1];
    }
}
