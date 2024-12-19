package com.studentvote.domain.poster.domain.repository;


import com.studentvote.domain.poster.domain.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PosterRepository extends JpaRepository<Poster, Long> {

    @Query("select p from Poster p " +
            "join p.user u " +
            "join Governance g on g.user.id = p.user.id " +
            "where g.governanceType = :governanceType")
    List<Poster> findAllByGovernance(@Param("governanceType") String governanceType);

}
