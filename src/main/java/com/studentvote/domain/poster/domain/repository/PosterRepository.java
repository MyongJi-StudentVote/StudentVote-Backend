package com.studentvote.domain.poster.domain.repository;


import com.studentvote.domain.poster.domain.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PosterRepository extends JpaRepository<Poster, Long> {

    @Query("SELECT DISTINCT p FROM Poster p " +
            "JOIN FETCH p.user u " +
            "JOIN FETCH u.governance g " +
            "WHERE g.governanceType = :governanceType")
    List<Poster> findAllByGovernance(@Param("governanceType") String governanceType);

}
