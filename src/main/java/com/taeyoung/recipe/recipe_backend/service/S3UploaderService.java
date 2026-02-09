//package com.taeyoung.recipe.recipe_backend.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//public class S3UploaderService {
//    private final S3Client s3Client;
//    private final String bucketName;
//    private final String cdnUrl;
//
//    public S3UploaderService(@Value("${cloud.aws.credentials.access-key}") String accessKey,
//                      @Value("${cloud.aws.credentials.secret-key}") String secretKey,
//                      @Value("${cloud.aws.region.static}") String region,
//                      @Value("${cloud.aws.s3.bucket}") String bucketName,
//                      @Value("${cdn.url}") String cdnUrl) {
//
//        this.bucketName = bucketName;
//        this.cdnUrl = cdnUrl;
//
//        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
//
//        this.s3Client = S3Client.builder()
//                .region(Region.of(region))
//                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
//                .build();
//    }
//
//    public String uploadFile(MultipartFile file) throws IOException {
//        if (file == null || file.isEmpty()) {
//            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
//        }
//
//        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
//
//        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(filename)
//                .contentType(file.getContentType())
//                .build();
//
//        s3Client.putObject(putObjectRequest,
//                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
//
//        return cdnUrl + "/" + filename; // CloudFront URL 반환
//    }
//}
