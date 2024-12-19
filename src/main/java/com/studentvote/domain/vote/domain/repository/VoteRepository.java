package com.studentvote.domain.vote.domain.repository;

import com.studentvote.domain.vote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
