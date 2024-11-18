package com.example.demoCRUD.controller;

import com.example.demoCRUD.dto.request.ApiRespon;
import com.example.demoCRUD.dto.request.UserCreateRequest;
import com.example.demoCRUD.dto.request.UserUpdateRequest;
import com.example.demoCRUD.entity.User;
import com.example.demoCRUD.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping // tao moi user
    ApiRespon<User> createUser(@RequestBody @Valid UserCreateRequest request){
        ApiRespon<User> apiRespon = new ApiRespon<>();
        apiRespon.setResult(userService.createUser(request));
        return apiRespon;
    }

    @GetMapping // tra ve list user
    List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}") // get theo id
    User getUser(@PathVariable("userId") String userId){
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}") // update user dua theo Id
    User updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}") // delete user theo id
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }
}
