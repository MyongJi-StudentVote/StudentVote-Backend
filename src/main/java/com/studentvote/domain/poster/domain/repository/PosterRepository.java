package com.studentvote.domain.poster.domain.repository;


import com.studentvote.domain.poster.domain.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PosterRepository extends JpaRepository<Poster, Long> {

    @Query("SELECT p FROM Poster p " +
            "JOIN FETCH p.user u " +
            "JOIN Governance g ON g.user.id = u.id " +
            "WHERE g.governanceType = :governanceType")
    List<Poster> findAllByGovernance(@Param("governanceType") String governanceType);
}
