package com.UserManagementApp.repository;

import com.UserManagementApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    public boolean existsByEmail(String email);

    public User findByEmail(String email);



}
