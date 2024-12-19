package com.studentvote.domain.poster.application;


import com.amazonaws.services.s3.AmazonS3;
import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.poster.domain.Poster;
import com.studentvote.domain.poster.domain.repository.PosterRepository;
import com.studentvote.domain.poster.dto.request.RegisterPosterRequest;
import com.studentvote.domain.user.domain.ApprovalStatus;
import com.studentvote.domain.user.domain.User;
import com.studentvote.global.config.s3.S3Service;
import com.studentvote.global.error.DefaultException;
import com.studentvote.global.payload.ErrorCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    private String makeFileName(User user, MultipartFile image) {
        String originName = image.getOriginalFilename();
        String ext = originName.substring(originName.lastIndexOf("."));
        String fileName = "posters/" + user.getId() + "/" + ext;
        return fileName;
    }

}
