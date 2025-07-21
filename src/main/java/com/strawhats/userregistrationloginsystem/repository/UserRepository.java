package com.strawhats.userregistrationloginsystem.repository;

import com.strawhats.userregistrationloginsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);

    boolean existsUserByUsername(String username);
}
