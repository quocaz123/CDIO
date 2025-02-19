package com.example.CDIO.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CDIO.domain.User;
import com.example.CDIO.domain.dto.ResultPaginationDTO;
import com.example.CDIO.service.UserService;
import com.example.CDIO.util.annotation.ApiMessage;
import com.example.CDIO.util.error.IdInvalidation;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    

    @PostMapping("/users")
    @ApiMessage("create a user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User poUser) throws IdInvalidation {
        boolean isEmailExist = this.userService.isEmailExist(poUser.getEmail());
        if (isEmailExist) {
            throw new IdInvalidation("Email " + poUser.getEmail() + " đã tồn tại, vui lòng sử dụng email khác...");
        }
        String hashPassword = this.passwordEncoder.encode(poUser.getPassword());
        poUser.setPassword(hashPassword);
        User user = this.userService.handleCreateUser(poUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/users/{id}")
    @ApiMessage("fetch a user by id")
    public ResponseEntity<User> getUserById(@Valid @PathVariable("id") long id) throws IdInvalidation {
        User currentUser = this.userService.fetchUserById(id);
        if (currentUser == null) {
            throw new IdInvalidation("User với id = " + id + " không tồn tại...");
        }
        return ResponseEntity.status(HttpStatus.OK).body(currentUser);
    }

    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<ResultPaginationDTO> fetchAllUsers(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser(pageable));
    }

    @PutMapping("users")
    @ApiMessage("update a user")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws IdInvalidation {
        User currentUser = this.userService.handleUpdateUser(user);
        if (currentUser == null) {
            throw new IdInvalidation("User với " + user.getId() + " không tồn tại...");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleUpdateUser(currentUser));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("delete a user")
    public ResponseEntity<User> deleteUser(@Valid @PathVariable("id") long id) throws IdInvalidation {
        User currentUser = this.userService.fetchUserById(id);
        if (currentUser == null) {
            throw new IdInvalidation("User với id = " + id + " không tồn tại...");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok(null);
    }

}
