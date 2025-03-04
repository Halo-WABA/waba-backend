package com.halo.eventer.domain.image.controller;

import com.halo.eventer.domain.image.dto.FileResDto;
import com.halo.eventer.domain.image.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "이미지 업로드", description = "각 부분에 대한 이미지 업로드")
public class ImageController {

  private static final Logger log = LoggerFactory.getLogger(ImageController.class);
  private final ImageService imageService;

  // stream 방식 업로드
  @PostMapping("/upload/stream")
  public FileResDto uploadStream(HttpServletRequest request) throws IOException {
    return new FileResDto(
        imageService.uploadStream(
            request.getInputStream(), request.getContentLength(), request.getContentType()));
  }

  // MultipartFile 방식 업로드
  @PostMapping("/upload/multipartFile")
  public FileResDto uploadImage(@RequestParam(value = "image", required = true) MultipartFile file)
      throws IOException {
    log.info("이미지 업로드 시작");
    return new FileResDto(imageService.upload(file));
  }

  // Pre Signed URL 방식
  @GetMapping("/upload/preSigned")
  public String getUploadUrl(@RequestParam String fileExtension) {
    return imageService.generatePreSignedUploadUrl(fileExtension);
  }
}
