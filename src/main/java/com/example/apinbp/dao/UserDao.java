package com.example.apinbp.dao;

import com.example.apinbp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public interface UserDao extends JpaRepository<User, Long> {
    User findByLogin(String username);

    @Query(value = "Select email from user WHERE login like ?1", nativeQuery = true)
    String selectUserEmailByUserLogin(String login);
}
