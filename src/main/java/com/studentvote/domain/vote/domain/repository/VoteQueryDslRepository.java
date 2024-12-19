package com.studentvote.domain.vote.domain.repository;

import com.studentvote.domain.user.domain.Governance;
import com.studentvote.domain.vote.dto.response.GetRateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoteQueryDslRepository {
    Page<GetRateResponse> findAllByGovernance(Governance governance, Pageable pageable);
}
