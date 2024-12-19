package com.studentvote.domain.vote.domain.repository;

import com.studentvote.domain.common.Status;
import com.studentvote.domain.vote.domain.VoteInformation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteInformationRepository extends JpaRepository<VoteInformation, Long> {
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM VoteInformation v WHERE v.status = :status")
    Boolean ExistsByStatus(Status status);

    Optional<VoteInformation> findByStatus(Status status);
}
