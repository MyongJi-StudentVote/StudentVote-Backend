package com.studentvote.domain.user.domain.repository;

import com.studentvote.domain.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    User findByUsername(String username);

    Optional<User> findByEmail(String email);
}
