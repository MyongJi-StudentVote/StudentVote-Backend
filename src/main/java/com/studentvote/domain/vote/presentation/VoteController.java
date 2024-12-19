package com.studentvote.domain.vote.presentation;

import com.studentvote.domain.vote.application.VoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Vote", description = "Vote API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vote")
public class VoteController {

    private final VoteService voteService;
}
