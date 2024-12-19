package com.studentvote.domain.user.application;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.poster.domain.Poster;
import com.studentvote.domain.poster.domain.PosterRepository;
import com.studentvote.domain.user.domain.User;
import com.studentvote.domain.user.domain.repository.UserRepository;
import com.studentvote.domain.user.dto.request.CandidateInfoListRequest;
import com.studentvote.domain.user.dto.response.CandidateInfoListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PosterRepository posterRepository;

    public CandidateInfoListResponse getCandidateInfo(CustomUserDetails userDetails) {
        List<Poster> posterList = posterRepository.findAll();
        posterList.stream().anyMatch(poster -> poster.getCandidateName())

    }

}
