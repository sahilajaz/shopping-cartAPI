package com.cart.web.cart_web.service.image;

import com.cart.web.cart_web.dto.ImageDto;
import com.cart.web.cart_web.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile>  files, Long productId);
    void updateImage(MultipartFile file , Long imageId);
}
