package com.khk.mgt.service;


import com.khk.mgt.dao.ProductCategoryDao;
import com.khk.mgt.ds.ProductCategory;
import com.khk.mgt.dto.common.ProductCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    public ProductCategory findProductCategoryById(Long id) {
        // Product Category find By ID
        return productCategoryDao.findById(id).orElse(null);
    }

    public boolean isProductCategoryExist(Long id) {
        // Product Category exists or not by ID
        return productCategoryDao.findById(id).isPresent();
    }

    public List<ProductCategory> findAllProductCategories() {
        // Retrieve the all Product Categories
        return productCategoryDao.findAll();
    }

    public List<ProductCategoryDto> getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryDao.findAll();

        if (productCategories.isEmpty()) {
            return null;
        }

        // Retrieve the all Product Categories convert to Dto
        return productCategories
                .stream()
                .map(category -> new ProductCategoryDto(category.getId(),category.getName()))
                .collect(Collectors.toList());
    }

    public void saveProductCategory(ProductCategoryDto productCategoryDto) {
        // Create Product Category Entity
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(productCategoryDto.getName());

        // Save the Product Category
        productCategoryDao.save(productCategory);
    }

    public void updateProductCategory(ProductCategoryDto productCategoryDto) {
        // Retrieve Product Category Entity
        ProductCategory productCategory = findProductCategoryById(productCategoryDto.getId());

        if (productCategory != null) {
            // Category Name Replace
            productCategory.setName(productCategoryDto.getName());
            productCategoryDao.save(productCategory);
        }
    }

    public void deleteProductCategory(Long id) {
        // Product Category Delete by ID
        productCategoryDao.deleteById(id);
    }
}
