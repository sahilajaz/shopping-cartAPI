package com.cart.web.cart_web.repository;

import com.cart.web.cart_web.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image , Long> {
}
