package com.cart.web.cart_web.service.image;

import com.cart.web.cart_web.dto.ImageDto;
import com.cart.web.cart_web.excpetions.ResourceNotFoundException;
import com.cart.web.cart_web.model.Image;
import com.cart.web.cart_web.model.Product;
import com.cart.web.cart_web.repository.ImageRepository;
import com.cart.web.cart_web.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with the id "+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete , () -> {
            throw new ResourceNotFoundException("No image found with the id "+id);
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto>  savedImageDtos = new ArrayList<>();

        for (MultipartFile file : files)  {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String  buildDownloadUrl="/api/v1/images/images/download/";

                String downlaodUrl = buildDownloadUrl+image.getId();
                image.setDownloadUrl(downlaodUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl +savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDtos.add(imageDto);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
          Image image = getImageById(imageId);

         try {
              image.setFileName(file.getOriginalFilename());
              image.setFileType(file.getContentType());
              image.setImage(new SerialBlob(file.getBytes()));
              imageRepository.save(image);
         }
         catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
         }
    }

}
