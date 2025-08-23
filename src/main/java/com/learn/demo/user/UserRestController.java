package com.learn.demo.user;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")  // Dev CORS (so the frond end request isn’t blocked)



@RestController
@RequestMapping("/users")
public class UserRestController {


    @Autowired
    private UserRepository userRepository;


    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User newUser) {

        if (newUser.getFirstName() == null || newUser.getFirstName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("First name is required");
        }
        if (newUser.getLastName() == null || newUser.getLastName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Last name is required");
        }
        if (newUser.getPassword() == null || newUser.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }
        if (newUser.getEmail() == null || newUser.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        userRepository.save(newUser);
        return new ResponseEntity<>("User " + newUser.getFirstName() + " " + newUser.getLastName() + " " + newUser.getPassword() + "is added", HttpStatusCode.valueOf(201));
    }








    @PutMapping("/updateUser/{userId}/password")
    public ResponseEntity<String> changePassword(
            @PathVariable("userId") int userId,
            @RequestBody Map<String, String> body) {

        String currentPassword = body.getOrDefault("currentPassword", "");
        String newPassword     = body.getOrDefault("newPassword", "");

        if (userId <= 0) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }

        return userRepository.findById(userId)
                .map(user -> {
                    if (currentPassword.isBlank() || newPassword.isBlank()) {
                        return ResponseEntity.badRequest().body("currentPassword and newPassword are required");
                    }


                    if (!user.getPassword().equals(currentPassword)) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect");
                    }


                    user.setPassword(newPassword);
                    userRepository.save(user);

                    return ResponseEntity.ok("Password updated successfully");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User " + userId + " not found"));
    }






    @DeleteMapping("/removeUser/{User_Id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int User_Id) {

        if (User_Id < 0) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        for (User user : userRepository.findAll()) {
            if (user.getUserId() == User_Id) {

                userRepository.delete(user);
                return new ResponseEntity<>("User " + user.getFirstName() + " " + user.getLastName() + " is Deleted", HttpStatusCode.valueOf(201));

            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


}
