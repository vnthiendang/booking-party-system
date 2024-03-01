package com.swp.repositories;


import com.swp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByEmail(String email);
    User findByUsId(Integer usId);
    @Query(value = "SELECT u.* " +
            "FROM user u " +
            " WHERE u.email = :email",
            nativeQuery = true)
    User checkEmailExist(String email);
}
