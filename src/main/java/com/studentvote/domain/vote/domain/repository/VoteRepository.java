package com.studentvote.domain.vote.domain.repository;

import com.studentvote.domain.user.domain.Governance;
import com.studentvote.domain.vote.domain.Vote;
import com.studentvote.domain.vote.dto.response.GetRateResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteRepository extends JpaRepository<Vote, Long>, VoteQueryDslRepository {
    Optional<Vote> findByGovernance(Governance governance);

    @Query("SELECT v FROM Vote v WHERE v.governance = :governance Order By v.createdAt DESC")
    List<Vote> findAllByGovernance(@Param("governance") Governance governance);
}
