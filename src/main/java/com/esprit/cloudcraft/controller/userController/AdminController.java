package com.esprit.cloudcraft.controller.userController;

import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.services.userServices.AdminService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private  UserRepository userRepository;
    /************** listing all registerd user ********************/
    @GetMapping("get")
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers()
    {

        List<User> users = adminService.getAllUsers();
        if (users == null || users.isEmpty()) {
            return ResponseEntity.notFound().build(); // Handle empty user list
        }
        return ResponseEntity.ok(users); // Return OK status with user list

    }
    @GetMapping("allusers")
    @ResponseBody
    public ResponseEntity<List<User>> get()
    {

        List<User> users = adminService.getAllUsers();
        if (users == null || users.isEmpty()) {
            return ResponseEntity.notFound().build(); // Handle empty user list
        }
        return ResponseEntity.ok(users); // Return OK status with user list

    }
    /******************** lock user api *************/
    @PatchMapping("/lock")
    public ResponseEntity<?> lockUser(@RequestParam("email") String email,@RequestParam("unlockdate") String unlockdate)
    {
        if (adminService.lockUser(email,unlockdate))
        {
            return ResponseEntity.accepted().build();
        } else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to lock user. User may already be locked or not found.");
        }
    }
    /******************* unlock user api ***********************/
    @PatchMapping("/unlock")
    public ResponseEntity<?> unlockUser(@RequestParam("email") String email)
    {
        if (adminService.unlockUser(email))
        {
            return ResponseEntity.accepted().build();
        } else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to lock user. User may already be locked or not found.");
        }
    }
    /*********** stats *************************/


    @GetMapping("/count-by-month-sql")
    @ResponseBody
    public Map<String,Long> countUsersByMonthJPQL() {
        Map<String, Long> result = new HashMap<>();
        List<Object[]> counts = userRepository.countUsersByMonthNativeQuery();
        for (Object[] row : counts) {

            Integer monthNumber = (Integer) row[0]; // Extract month as Integer
            Long count = (Long) row[1]; // Extract count as Long

            String monthName = Month.of(monthNumber).name(); // Get month name from enum

            result.put(monthName, count);
        }
        return result;

    }
    @GetMapping("/count-by-locked-month-sql")
    @ResponseBody
    public Map<String,Long> countUserslockedByMonthNativeQuery() {
        Map<String, Long> result = new HashMap<>();
        List<Object[]> counts = userRepository.countUserslockedByMonthNativeQuery();
        for (Object[] row : counts) {

            Integer monthNumber = (Integer) row[0]; // Extract month as Integer
            Long count = (Long) row[1]; // Extract count as Long

            String monthName = Month.of(monthNumber).name(); // Get month name from enum

            result.put(monthName, count);
        }
        return result;

    }
    @GetMapping("/count-user-by-class")
    @ResponseBody
    public Map<String,Long> numberofusersbyClasstype() {
        Map<String, Long> result = new HashMap<>();
        List<Object[]> counts = userRepository.numberofusersbyClasstype();
        for (Object[] row : counts) {

            String classe = (String) row[0]; // Extract month as Integer
            Long count = (Long) row[1]; // Extract count as Long


            result.put(classe, count);
        }
        return result;

    }
    @GetMapping("/session_per_day")
    @ResponseBody
    public Map<Integer,Long> countSessionsByDayCurrentYearAndMonth() {
        Map<Integer, Long> result = new HashMap<>();
        List<Object[]> counts = userRepository.countSessionsByDayCurrentYearAndMonth();
        for (Object[] row : counts) {

            Integer dayNumber = (Integer) row[0]; // Extract month as Integer



            Long count = (Long) row[1]; // Extract count as Long


            result.put(dayNumber, count);
        }
        return result;

    }
    @GetMapping("/count/current-year")
    public ResponseEntity<Long> numberofuserscurrentyear() {
        Long count = userRepository.numberofuserscurrentyear().get(0);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/current-year-disabled")
    public ResponseEntity<Long> numberofuserscurrentyeardisabled() {
        Long count = userRepository.numberofuserscurrentyeardisabled().get(0);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/users-without-at")
    public ResponseEntity<Long> countUsersWithoutAtSymbol() {
        Long count = userRepository.countUsersWithEmailWithoutAtSymbol();
        return ResponseEntity.ok(count);
    }


}
