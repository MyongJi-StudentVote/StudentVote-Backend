package com.studentvote.domain.vote.application;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.common.Status;
import com.studentvote.domain.vote.domain.VoteInformation;
import com.studentvote.domain.vote.domain.repository.VoteInformationRepository;
import com.studentvote.domain.vote.domain.repository.VoteRepository;
import com.studentvote.domain.vote.dto.request.CreateVoteRequest;
import com.studentvote.domain.vote.exception.AlreadyExistVoteInformationException;
import com.studentvote.global.config.s3.S3Service;
import com.studentvote.global.payload.Message;
import java.util.Optional;
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
    private final VoteRepository voteRepository;

    @Transactional
    public Message createVote(CustomUserDetails userDetails, CreateVoteRequest createVoteRequest) {

        if (!userDetails.getUsername().equals(MASTER_USERNAME)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        Boolean existsByStatus = voteInformationRepository.ExistsByStatus(Status.ACTIVE);

        if (existsByStatus) {
            throw new AlreadyExistVoteInformationException();
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

    @Transactional
    public Message reset(CustomUserDetails userDetails) {

        if (!userDetails.getUsername().equals(MASTER_USERNAME)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        Optional<VoteInformation> byStatus = voteInformationRepository.findByStatus(Status.ACTIVE);

        if (byStatus.isPresent()) {
            VoteInformation voteInformation = byStatus.get();
            voteInformation.updateStatus(Status.DELETE);
        }

        /*
        여기에 삭제하고 싶은 모든 데이터 레포지토리 아래처럼 차례로 추가하면 됨.
         */
        voteRepository.deleteAll();

        return Message
                .builder()
                .message("선거 데이터를 초기화했습니다.")
                .build();
    }
}
