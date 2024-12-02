package com.studentvote.domain.auth.domain.repository;

import com.studentvote.domain.auth.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
