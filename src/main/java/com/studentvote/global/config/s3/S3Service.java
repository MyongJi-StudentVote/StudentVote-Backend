package com.studentvote.global.config.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.studentvote.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String createFileName(String originalFilename) {
        return UUID.randomUUID().toString() + "_" + originalFilename;
    }

    public String uploadImageToS3(MultipartFile image) {
        try {
            String originName = image.getOriginalFilename();
            String ext = originName.substring(originName.lastIndexOf("."));
            String fileName = createFileName(ext);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(image.getContentType());
            metadata.setContentLength(image.getSize());

            amazonS3.putObject(
                    new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata)
            );
            return amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("ImageUploadException");
        }
    }

    public String uploadImageToS3(MultipartFile image, String fileName) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(image.getContentType());
            metadata.setContentLength(image.getSize());

            amazonS3.putObject(
                    new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata)
            );
            return amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("ImageUploadException");
        }
    }

    public void deleteImageFromS3(String imageUrl) {
        try {
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            amazonS3.deleteObject(bucket, fileName);
        } catch (Exception e) {
            throw new RuntimeException("Image delete failed", e);
        }
    }


}
