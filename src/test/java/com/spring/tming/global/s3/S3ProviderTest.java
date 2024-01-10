package com.spring.tming.global.s3;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.AmazonS3;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class S3ProviderTest {
    @InjectMocks private S3Provider s3Provider;
    @Mock private AmazonS3 amazonS3;

    @Test
    @DisplayName("파일 저장 테스트")
    void 파일_저장() throws IOException {
        // given
        String folderName = "test";
        String filename = "images/sparta.png";
        Resource resource = new ClassPathResource(filename);
        MultipartFile multipartFile =
                new MockMultipartFile(
                        "sparta", filename, "image/png", Files.readAllBytes(resource.getFile().toPath()));
        when(amazonS3.getUrl(any(), any())).thenReturn(new URL("https://example.com/mock-url"));

        // when
        s3Provider.saveFile(multipartFile, folderName);

        // then
        verify(amazonS3, times(2)).putObject(any(), any(), any(), any());
        verify(amazonS3).doesObjectExist(any(), any());
        verify(amazonS3).getUrl(any(), any());
    }

    @Test
    @DisplayName("파일 수정 테스트")
    void 파일_수정() throws IOException {
        // given
        String filename = "images/sparta.png";
        Resource resource = new ClassPathResource(filename);
        MultipartFile multipartFile =
                new MockMultipartFile(
                        "sparta", filename, "image/png", Files.readAllBytes(resource.getFile().toPath()));
        when(amazonS3.getUrl(any(), any())).thenReturn(new URL("https://example.com/mock-url"));
        when(amazonS3.doesObjectExist(any(), any())).thenReturn(true);

        // when
        s3Provider.updateImage("test/" + filename, multipartFile);

        // then
        verify(amazonS3).doesObjectExist(any(), any());
        verify(amazonS3).putObject(any(), any(), any(), any());
        verify(amazonS3).getUrl(any(), any());
    }
}
