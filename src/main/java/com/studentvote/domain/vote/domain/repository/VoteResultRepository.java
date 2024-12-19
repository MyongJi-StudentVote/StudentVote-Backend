package com.studentvote.domain.vote.domain.repository;

import com.studentvote.domain.user.domain.Governance;
import com.studentvote.domain.vote.domain.VoteResult;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {
    Optional<VoteResult> findByGovernance(Governance governance);

    @Query("select count(v) > 0 from VoteResult v where v.governance = :governance")
    Boolean ExistsByGovernance(Governance governance);
}
