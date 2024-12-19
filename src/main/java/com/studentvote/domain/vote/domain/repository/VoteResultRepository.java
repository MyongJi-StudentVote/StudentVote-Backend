package com.studentvote.domain.vote.domain.repository;

import com.studentvote.domain.vote.domain.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {
}
