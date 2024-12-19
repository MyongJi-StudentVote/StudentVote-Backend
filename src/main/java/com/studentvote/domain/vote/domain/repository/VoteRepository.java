package com.studentvote.domain.vote.domain.repository;

import com.studentvote.domain.user.domain.Governance;
import com.studentvote.domain.vote.domain.Vote;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long>, VoteQueryDslRepository {
    Optional<Vote> findByGovernance(Governance governance);
}
