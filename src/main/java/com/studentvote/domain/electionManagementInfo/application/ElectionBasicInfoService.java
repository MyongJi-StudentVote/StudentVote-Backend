package com.studentvote.domain.electionManagementInfo.application;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.studentvote.domain.electionManagementInfo.domain.ElectionBasicInfo;
import com.studentvote.domain.electionManagementInfo.domain.repository.ElectionBasicInfoRepository;
import com.studentvote.domain.electionManagementInfo.dto.response.ElectionBasicInfoResponse;
import com.studentvote.global.error.DefaultException;
import com.studentvote.global.payload.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ElectionBasicInfoService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final ElectionBasicInfoRepository electionBasicInfoRepository;

    public ElectionBasicInfoResponse uploadElectionInfo(MultipartFile electionCommitteeNotice, MultipartFile electionRegulationAmendmentNotice, MultipartFile candidateRecruitmentNotice, MultipartFile electionRegulation) {
        String committeeNoticeUrl = uploadImageToS3(electionCommitteeNotice);
        String regulationAmendmentUrl = uploadImageToS3(electionRegulationAmendmentNotice);
        String recruitmentNoticeUrl = uploadImageToS3(candidateRecruitmentNotice);
        String regulationUrl = uploadImageToS3(electionRegulation);

        ElectionBasicInfo savedEntity = electionBasicInfoRepository.save(
                new ElectionBasicInfo(null, committeeNoticeUrl, regulationAmendmentUrl, recruitmentNoticeUrl, regulationUrl)
        );
        return new ElectionBasicInfoResponse(
                savedEntity.getId(),
                savedEntity.getElectionCommitteeNotice(),
                savedEntity.getElectionRegulationAmendmentNotice(),
                savedEntity.getCandidateRecruitmentNotice(),
                savedEntity.getElectionRegulation()
        );
    }

    public String uploadImageToS3(MultipartFile image) {
        String originName = image.getOriginalFilename();
        String ext = originName.substring(originName.lastIndexOf("."));
        String changedName = changedImageName(originName);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType("image/" + ext);

        try {
            PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(
                    bucketName, changedName, image.getInputStream(), metadata
            ));
        } catch (IOException e) {
            throw new RuntimeException("ImageUploadException");
        }
        return amazonS3.getUrl(bucketName, changedName).toString();
    }

    private String changedImageName(String originName) {
        String random = UUID.randomUUID().toString();
        return random +'-' + originName;
    }

}