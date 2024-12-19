package com.studentvote.domain.poster.application;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.candidateInfo.domain.CandidateInfoRepository;
import com.studentvote.domain.poster.domain.Poster;
import com.studentvote.domain.poster.domain.repository.PosterRepository;
import com.studentvote.domain.poster.dto.request.RegisterPosterRequest;
import com.studentvote.domain.poster.dto.response.GetAllPosterResponse;
import com.studentvote.domain.poster.dto.response.GetPosterResponse;
import com.studentvote.domain.user.domain.ApprovalStatus;
import com.studentvote.domain.user.domain.User;
import com.studentvote.global.config.s3.S3Service;
import com.studentvote.global.error.DefaultException;
import com.studentvote.global.payload.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PosterService {

    private final PosterRepository posterRepository;
    private final S3Service s3Service;

    @Transactional
    public Poster registerPoster(CustomUserDetails userDetails, RegisterPosterRequest request) {
        User user = userDetails.user();
        if (user.getApprovalStatus() != ApprovalStatus.APPROVED) {
            throw new DefaultException(ErrorCode.STATUS_NOT_APPROVED);
        }
        String fileName = makeFileName(user, request.posterImage());
        s3Service.uploadImageToS3(request.posterImage(), fileName);
        Poster poster = posterRepository.save(new Poster(null, request.posterName(), fileName, user));
        return poster;
    }

    @Transactional
    public Poster deletePoster(CustomUserDetails userDetails, Long posterId) {
        User user = userDetails.user();
        Poster poster = posterRepository.findById(posterId).orElseThrow(() -> new DefaultException(ErrorCode.POSTER_NOT_FOUND));
        if (!poster.getUser().getId().equals(user.getId())) {
            throw new DefaultException(ErrorCode.POSTER_ACCESS_DENIED);
        }
        s3Service.deleteImageFromS3(poster.getPosterImage());
        posterRepository.delete(poster);
        return poster;
    }

    @Transactional(readOnly = true)
    public Poster getPosterById(CustomUserDetails userDetails, Long postId) {
        Poster poster = posterRepository.findById(postId).orElseThrow(() -> new DefaultException(ErrorCode.POSTER_NOT_FOUND));
        return poster;
    }

    @Transactional(readOnly = true)
    public GetAllPosterResponse getAllPosterByGovernance(CustomUserDetails userDetails, String governanceType) {
        List<Poster> posterList = posterRepository.findAllByGovernance(governanceType);
        List<GetAllPosterResponse.PosterResponse> list = posterList.stream().map(poster -> new GetAllPosterResponse.PosterResponse(
                        poster.getId(),
                        poster.getPosterName(),
                        poster.getPosterImage(),
                        poster.getUser().getId(),
                        poster.getUser().getCandidateInfo().getCandidateName()
                        ))
                .toList();
        return new GetAllPosterResponse(list);
    }

    private String makeFileName(User user, MultipartFile image) {
        String originName = image.getOriginalFilename();
        String ext = originName.substring(originName.lastIndexOf("."));
        String fileName = "posters/" + user.getId() + "/" + ext;
        return fileName;
    }

}
