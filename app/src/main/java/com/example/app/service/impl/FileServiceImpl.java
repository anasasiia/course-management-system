package com.example.app.service.impl;

import com.example.app.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${spring.servlet.multipart.location}")
    private String location;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Path.of(location));
        } catch (IOException e) {
            log.error("Failed to save file. Error: " + e.getLocalizedMessage());
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to save empty file");
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try(InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, Path.of(location).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to save file " + fileName + ". Error: " + e.getLocalizedMessage());
        }
        return fileName;
    }
}
