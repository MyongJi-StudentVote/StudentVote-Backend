package com.studentvote.domain.vote.application;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.common.Status;
import com.studentvote.domain.user.domain.Governance;
import com.studentvote.domain.user.domain.User;
import com.studentvote.domain.user.domain.repository.GovernanceRepository;
import com.studentvote.domain.user.domain.repository.UserRepository;
import com.studentvote.domain.vote.domain.Vote;
import com.studentvote.domain.vote.domain.VoteInformation;
import com.studentvote.domain.vote.domain.VoteResult;
import com.studentvote.domain.vote.domain.repository.VoteInformationRepository;
import com.studentvote.domain.vote.domain.repository.VoteRepository;
import com.studentvote.domain.vote.domain.repository.VoteResultRepository;
import com.studentvote.domain.vote.dto.request.CreateVoteRequest;
import com.studentvote.domain.vote.dto.request.RegisterVoteRateRequest;
import com.studentvote.domain.vote.dto.response.GetRateResponse;
import com.studentvote.domain.vote.dto.response.GetResultResponse;
import com.studentvote.domain.vote.exception.AlreadyExistVoteInformationException;
import com.studentvote.domain.vote.exception.AlreadyExistVoteResultException;
import com.studentvote.global.config.s3.S3Service;
import com.studentvote.global.payload.Message;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class VoteService {

    private static final String MASTER_USERNAME = "ADMIN";

    private final S3Service s3Service;

    private final VoteInformationRepository voteInformationRepository;
    private final VoteRepository voteRepository;
    private final GovernanceRepository governanceRepository;
    private final UserRepository userRepository;
    private final VoteResultRepository voteResultRepository;

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

    @Transactional
    public Message registerVoteRate(CustomUserDetails userDetails, Long departmentId,
                                    RegisterVoteRateRequest registerVoteRateRequest) {

        User user = userRepository.findByEmail(userDetails.user().getEmail())
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저입니다."));

        Governance governance = governanceRepository.findById(departmentId)
                .orElseThrow(() -> new NullPointerException("해당 학과/단과대를 찾을 수 없습니다."));

        if (user.getId().equals(user.getGovernance().getVoteHeadquaterUserId()) && user.getGovernance().getId().equals(departmentId)) {
            Vote vote = Vote.of(registerVoteRateRequest.voteCount(), registerVoteRateRequest.voteRate(), governance);
            voteRepository.save(vote);
        }

        return Message
                .builder()
                .message("저장이 완료되었습니다.")
                .build();
    }

    public Page<GetRateResponse> getRate(Long departmentId, Pageable pageable) {

        Governance governance = governanceRepository.findById(departmentId)
                .orElseThrow(() -> new NullPointerException("해당 학과/단과대를 찾을 수 없습니다."));

        Page<GetRateResponse> getRateResponses = voteRepository.findAllByGovernance(governance, pageable);

        return getRateResponses;
    }

    @Transactional
    public Message postResult(CustomUserDetails userDetails, MultipartFile file) {

        User user = userRepository.findByEmail(userDetails.user().getEmail())
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저입니다."));

        String image = s3Service.uploadImageToS3(file);

        Governance governance = governanceRepository.findByVoteHeadquaterUserId(user.getId())
                .orElseThrow(() -> new NullPointerException("해당하는 자치기구가 없습니다."));

        Boolean existsedByGovernance = voteResultRepository.ExistsByGovernance(governance);

        if (existsedByGovernance) {
            throw new AlreadyExistVoteResultException();
        }

        VoteResult voteResult = VoteResult.of(image, governance);

        voteResultRepository.save(voteResult);

        return Message
                .builder()
                .message("개표 결과 저장이 완료되었습니다.")
                .build();
    }

    public GetResultResponse getResult(Long governanceId) {
        Governance governance = governanceRepository.findById(governanceId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 자치기구 id가 유효하지 않습니다."));

        Optional<VoteResult> optionalVoteResult = voteResultRepository.findByGovernance(governance);

        String deptName = governance.getGovernanceName().isEmpty() ? governance.getGovernanceType() : governance.getGovernanceName();

        if (optionalVoteResult.isPresent()) {
            VoteResult voteResult = optionalVoteResult.get();


            GetResultResponse getResultResponse = GetResultResponse
                    .builder()
                    .departmentName(deptName)
                    .resultImageUrl(voteResult.getResultImage())
                    .build();

            return getResultResponse;
        }

        GetResultResponse
                .builder()
                .departmentName(deptName)
                .resultImageUrl(null)
                .build();
        return null;
    }
}
