package com.studentvote.domain.poster.domain.repository;


import com.studentvote.domain.poster.domain.Poster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosterRepository extends JpaRepository<Poster, Long> {
}
