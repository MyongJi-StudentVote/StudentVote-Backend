package com.studentvote.domain.vote.application;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.vote.domain.VoteInformation;
import com.studentvote.domain.vote.domain.repository.VoteInformationRepository;
import com.studentvote.domain.vote.dto.request.CreateVoteRequest;
import com.studentvote.global.config.s3.S3Service;
import com.studentvote.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class VoteService {

    private static final String MASTER_USERNAME = "ADMIN";

    private final S3Service s3Service;

    private final VoteInformationRepository voteInformationRepository;

    @Transactional
    public Message createVote(CustomUserDetails userDetails, CreateVoteRequest createVoteRequest) {

        if (!userDetails.getUsername().equals(MASTER_USERNAME)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        String image = s3Service.uploadImageToS3(createVoteRequest.file());

        VoteInformation voteInformation = VoteInformation.of(createVoteRequest.voteName(),
                createVoteRequest.voteDateTime(),
                createVoteRequest.voteDescription(), image);

        voteInformationRepository.save(voteInformation);

        return Message
                .builder()
                .message("선거 메타데이터 생성이 완료되었습니다.")
                .build();
    }
}
