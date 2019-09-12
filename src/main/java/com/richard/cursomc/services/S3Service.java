package com.richard.cursomc.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.richard.cursomc.services.exceptions.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Richard Rossetto on 9/18/18.
 */
@Service
public class S3Service {

    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(inputStream, fileName, contentType);
        } catch (IOException e) {
            throw new FileException("Erro de IO: " + e.getMessage());
        }
    }


    public URI uploadFile(InputStream is, String fileName, String contentType) {

        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            logger.info("Iniciando upload..");
            s3Client.putObject(bucketName, fileName, is, meta);
            logger.info("Upload finalizado!");
            return s3Client.getUrl(bucketName, fileName).toURI();
        } catch (AmazonServiceException e) {
            logger.info("AmazonServiceException : " + e.getErrorMessage());
            logger.info("Status code : " + e.getErrorCode());
            e.printStackTrace();
        } catch (AmazonClientException e) {
            logger.info("AmazonClientException :  ", e.getMessage());
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
        return null;
    }
}