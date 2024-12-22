package com.studentvote.domain.candidateInfo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CandidateInfoRepository extends JpaRepository<CandidateInfo, Long> {

    @Query("select c from CandidateInfo c " +
            "join c.user u " +
            "join u.governance g " +
            "where u.id = :userId " +
            "and g.id = :governanceId")
    List<CandidateInfo> findAllCandidateByGovernanceType(@Param("userId") Long userId, @Param("governanceId") String governanceId);


    @Query("select c from CandidateInfo c "
            + "join c.user u "
            + "join u.governance g "
            + "where g.id = :governanceId")
    List<CandidateInfo> findAllByGovernanceId(String governanceId);
}
