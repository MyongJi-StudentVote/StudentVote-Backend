package com.studentvote.domain.vote.domain.repository;

import com.studentvote.domain.vote.domain.VoteInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteInformationRepository extends JpaRepository<VoteInformation, Long> {
}
