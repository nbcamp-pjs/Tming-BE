package com.spring.tming.domain.post.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageFileHandler {
    public static String uploadImage(MultipartFile image) throws IOException {
        try {
            String imageUrl = "";
            Path uploadPath = Paths.get("./upload");
            // 디렉토리가 없는 경우 생성하기
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath);

            imageUrl = String.valueOf(filePath);
            return imageUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
