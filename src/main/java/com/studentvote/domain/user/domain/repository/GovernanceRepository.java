package com.studentvote.domain.user.domain.repository;

import com.studentvote.domain.user.domain.Governance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GovernanceRepository extends JpaRepository<Governance, Long> {
}
