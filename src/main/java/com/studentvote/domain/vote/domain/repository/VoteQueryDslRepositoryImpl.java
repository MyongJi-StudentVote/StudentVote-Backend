package com.studentvote.domain.vote.domain.repository;

import static com.studentvote.domain.vote.domain.QVote.vote;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studentvote.domain.user.domain.Governance;
import com.studentvote.domain.vote.dto.response.GetRateResponse;
import com.studentvote.domain.vote.dto.response.QGetRateResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VoteQueryDslRepositoryImpl implements VoteQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GetRateResponse> findAllByGovernance(Governance governance, Pageable pageable) {

        List<GetRateResponse> results = queryFactory
                .select(new QGetRateResponse(
                        vote.createdAt,
                        vote.voteCount,
                        vote.voteRate
                ))
                .from(vote)
                .where(vote.governance.eq(governance))
                .orderBy(vote.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(vote.count())
                .from(vote)
                .where(vote.governance.eq(governance));

        return PageableExecutionUtils.getPage(results, pageable, () -> countQuery.fetch().size());
    }
}
