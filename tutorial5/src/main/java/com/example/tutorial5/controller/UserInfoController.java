package com.example.tutorial5.controller;

import com.example.tutorial5.entity.User;
import com.example.tutorial5.repository.UserRepository;
import com.example.tutorial5.request.AddUserRequest;
import com.example.tutorial5.response.GetAllUsersResponse;
import com.example.tutorial5.response.GetUserResponse;
import com.example.tutorial5.response.UserWebResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private UserRepository userRepository;

    @Autowired
    public UserInfoController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * used for getting all users
     * @return list of all users
     */
    @GetMapping("/users")
    public ResponseEntity<GetAllUsersResponse> getAllUsers() {

        try {
            System.out.println("Trying to find the users....");
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GetAllUsersResponse("No users found", false, null));
            }

            return ResponseEntity.ok().body(new GetAllUsersResponse("Users retrieved", true, users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GetAllUsersResponse("Failed to retrieve users", false, null));
        }
    }

    /**
     *
     * @param addUserRequest - contains user info to be added
     * @return success flag and a message
     */
    @PostMapping("/add")
    public ResponseEntity<UserWebResponse> addNewUser(@RequestBody AddUserRequest addUserRequest) {

        try {
            System.out.println("Inside add: "+ addUserRequest.toString());
            UserWebResponse userWebResponse = new UserWebResponse();

            if (userRepository.findByEmail(addUserRequest.getEmail()) != null) {
                userWebResponse.setMessage("User with this email already exists!");
                userWebResponse.setSuccess(false);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userWebResponse);
            }

           // user.setId(ObjectId.get().toString());
            User newUser = new User();

            newUser.setEmail(addUserRequest.getEmail());
            newUser.setFirstName(addUserRequest.getFirstName());

            userRepository.save(newUser);

            return ResponseEntity.ok().body(new UserWebResponse("User added", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserWebResponse("Failed to add user", false));
        }
    }

    /**
     *
     * @param userId - for which the info needs to be updated
     * @param updatedUserDetails - new details
     * @return success flag and a message
     */
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserWebResponse> updateUserDetailsById(@PathVariable String userId, @RequestBody User updatedUserDetails) {

        try {
            User currentUser = userRepository.findById(userId).orElse(null);
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserWebResponse("User not found", false));
            }


            if (updatedUserDetails.getEmail() != null) {
                currentUser.setEmail(updatedUserDetails.getEmail());
            }


            if (updatedUserDetails.getFirstName() != null) {
                currentUser.setFirstName(updatedUserDetails.getFirstName());
            }

            userRepository.save(currentUser);

            return ResponseEntity.ok().body(new UserWebResponse("User updated", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserWebResponse("Failed to update user", false));
        }
    }

    /**
     *
     * @param userId - for which info needs to be fetched
     * @return the user info for the passed userId
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable String userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GetUserResponse(false, null));
            }
            return ResponseEntity.ok().body(new GetUserResponse(true, user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GetUserResponse(false, null));
        }
    }


}
