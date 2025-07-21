package com.strawhats.userregistrationloginsystem.repository;

import com.strawhats.userregistrationloginsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
