package com.spring.tming.domain.post.util;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.global.s3.S3Provider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageFileHandler {
    private static S3Provider s3Provider;

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

    public static String checkUpdateImage(Post post, MultipartFile image) throws IOException {
        String imageUrl = null;
        // 기존에 데이터가 있는데 들어온 값에 데이터가 없는 경우 -> 삭제
        // 기존에 데이터가 있는데 들어온 값에 데이터가 있는 경우 -> 수정
        // 기존에 데이터가 없는데 들어온 값에 데이터가 있는 경우 -> 생성
        if (post.getImageUrl() != null && (image == null || image.isEmpty())) {
            String[] url = post.getImageUrl().split("/");
            s3Provider.deleteImage(url[url.length - 2] + "/" + url[url.length - 1]);
        } else if (post.getImageUrl() != null) {
            String[] url = post.getImageUrl().split("/");
            imageUrl = s3Provider.updateImage(url[url.length - 2] + "/" + url[url.length - 1], image);
        } else if (image != null) {
            imageUrl = s3Provider.saveFile(image, "postImage");
        }
        return imageUrl; // 예상값 null or Url
    }
}
