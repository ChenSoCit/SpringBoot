package com.example.demoCRUD.reponsitory;

import com.example.demoCRUD.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReponsitory extends JpaRepository<User, String> {

    boolean existsByUsername(String username);


    Optional<User> findByUsername(String username);
}
