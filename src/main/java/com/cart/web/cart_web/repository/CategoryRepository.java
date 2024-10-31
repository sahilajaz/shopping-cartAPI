package com.cart.web.cart_web.repository;

import com.cart.web.cart_web.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Long> {
    Category findByName(String name);
}
