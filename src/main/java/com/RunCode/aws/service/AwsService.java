package com.RunCode.aws.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AwsService {

    private final S3Client s3Client;
    private final S3Presigner presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // 업로드용 presigned URL
    public String getUploadImagePresignedUrl(String imageName, String contentType) {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("archivings/" + imageName)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5)) // 5분 유효
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presigned =
                presigner.presignPutObject(presignRequest);

        return presigned.url().toString();
    }

    // 조회용 presigned URL
    public String getDownloadPresignedUrl(String filename) {
        if (filename == null || filename.isBlank()) {
            return null;
        }

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key("archivings/" +filename)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presigned =
                presigner.presignGetObject(presignRequest);

        return presigned.url().toString();
    }
}
