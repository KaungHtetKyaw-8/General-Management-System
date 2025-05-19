package com.khk.mgt.service;

import com.khk.mgt.dao.PointCardCategoryDao;
import com.khk.mgt.dao.ProductCategoryDao;
import com.khk.mgt.dao.ProductDao;
import com.khk.mgt.dao.VendorDao;
import com.khk.mgt.ds.Product;
import com.khk.mgt.ds.Vendor;
import com.khk.mgt.dto.common.InventoryDto;
import com.khk.mgt.dto.common.SelectionDto;
import com.khk.mgt.dto.common.VendorDto;
import com.khk.mgt.mapper.InventoryMapper;
import com.khk.mgt.mapper.VendorMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class InventoryService {
    @Autowired
    private VendorService vendorService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductDao productDao;

    public Product findProductById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    public boolean isProductExist(Long id) {
        return productDao.findById(id).isPresent();
    }

    public List<InventoryDto> getAllProducts() {
        List<Product> products = productDao.findAll();

        if (products.isEmpty()) {
            return null;
        }

        return products
                .stream().map(InventoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public InventoryDto getProductById(Long id) {
        Product product = productDao.findById(id).orElse(null);

        if (product == null) {
            return null;
        }

        return InventoryMapper.toDto(product);
    }

    public List<InventoryDto> getVendorById(Long id) {
        List<Product> product = productDao.findByVendor_Id(id);

        if (product == null || product.isEmpty()) {
            return null;
        }

        return product.stream()
                .map(InventoryMapper::toDto)
                .toList();
    }

    public List<InventoryDto> getCategoryById(Long id) {
        List<Product> product = productDao.findByCategory_Id(id);

        if (product == null || product.isEmpty()) {
            return null;
        }

        return product.stream()
                .map(InventoryMapper::toDto)
                .toList();
    }
    
    public void saveProduct(InventoryDto inventoryDto) {
        // Dto to Entity Convert
        Product product = InventoryMapper.toEntity(inventoryDto);
        product.setVendor(vendorService.findVendorById(inventoryDto.getVendorId()));
        product.setCategory(productCategoryService.findProductCategoryById(inventoryDto.getCategoryId()));

        // Save the Customer
        productDao.save(product);
    }

    public void updateProduct(InventoryDto inventoryDto) {
        Product existProduct = findProductById(inventoryDto.getId());

        if (existProduct != null) {
            Product updateProduct = InventoryMapper.toEntity(inventoryDto);

            // Name Prices Count Modify
            BeanUtils.copyProperties(updateProduct, existProduct);

            // Category and Vendor modify
            existProduct.setCategory(productCategoryService.findProductCategoryById(inventoryDto.getCategoryId()));
            existProduct.setVendor(vendorService.findVendorById(inventoryDto.getVendorId()));

            // Update the product
            productDao.save(existProduct);
        }
    }

    public void deleteProduct(Long id) {
        productDao.deleteById(id);
    }
}
