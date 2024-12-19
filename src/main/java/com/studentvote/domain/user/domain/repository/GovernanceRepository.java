package com.studentvote.domain.user.domain.repository;

import com.studentvote.domain.user.domain.Governance;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GovernanceRepository extends JpaRepository<Governance, Long> {
    Optional<Governance> findByVoteHeadquaterUserId(Long voteHeadquaterUserId);
}
