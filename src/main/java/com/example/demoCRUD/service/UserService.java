package com.example.demoCRUD.service;

import com.example.demoCRUD.dto.request.UserCreateRequest;
import com.example.demoCRUD.dto.request.UserUpdateRequest;
import com.example.demoCRUD.entity.User;
import com.example.demoCRUD.exception.AppException;
import com.example.demoCRUD.exception.ErrorCode;
import com.example.demoCRUD.reponsitory.UserReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserReponsitory userReponsitory;

    // Tao method tao user

    public User createUser(UserCreateRequest request){ // xu ly tao user
        User user = new User();

        if(userReponsitory.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));



        user.setUsername(request.getUsername());

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setDob(request.getDob());

        return userReponsitory.save(user);
    }
    public List<User> getUsers(){ // xu ly get all
        return userReponsitory.findAll();
    }

    public User getUser(String id){ // xu ly get userId
        return userReponsitory.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }

    public User updateUser(String userId, UserUpdateRequest request){ // xu ly update user dua vao id_user
        User user = getUser(userId);

        user.setPassword(request.getPassword());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setDob(request.getDob());
        return userReponsitory.save(user);
    }

    public void deleteUser(String userId){ // xoa user dua vao Id_user
        userReponsitory.deleteById(userId);
    }
}
