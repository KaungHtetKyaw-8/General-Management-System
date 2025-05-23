package com.khk.mgt.service;

import com.khk.mgt.dao.ProductDao;
import com.khk.mgt.ds.Product;
import com.khk.mgt.dto.chart.GroupedLabelValue;
import com.khk.mgt.dto.chart.LabelValue;
import com.khk.mgt.dto.common.InventoryDto;
import com.khk.mgt.dto.common.PosProductDto;
import com.khk.mgt.mapper.InventoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public InventoryDto getInventoryById(Long id) {
        Product product = productDao.findById(id).orElse(null);

        if (product == null) {
            return null;
        }

        return InventoryMapper.toDto(product);
    }

    public PosProductDto getProductById(Long id) {
        Product product = productDao.findById(id).orElse(null);

        if (product == null) {
            return null;
        }

        return new PosProductDto(product.getId(),product.getName(),product.getCount(),product.getSellPrice());
    }

    public List<PosProductDto> getItemListByCategoryId(Long id) {
        List<Product> products = productDao.findByCategory_Id(id);

        if (products == null || products.isEmpty()) {
            return null;
        }

        return products
                .stream()
                .map(item->{
                    return new PosProductDto(item.getId(),item.getName(),item.getCount(),item.getSellPrice());
                }).toList();
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

    public List<LabelValue> getVendorGenderCount(){
        return vendorService.getCountByGender();
    }

    public List<LabelValue> getCountByVendor(){
        return productDao.findProductCountByVendor();
    }

    public List<LabelValue> getCountByVendorCompany(){
        return productDao.findProductCountByVendorCompany();
    }

    public List<GroupedLabelValue> getMultiChartDataByBuyPrice(){
        List<Product> productList = productDao.findTop10ByOrderByBuyPriceDesc();

        List<GroupedLabelValue> result = new ArrayList<>();

        productList.forEach(item->{
            result.add(new GroupedLabelValue(item.getName(),"SellPrice",item.getSellPrice()));
            result.add(new GroupedLabelValue(item.getName(),"BuyPrice",item.getBuyPrice()));
            result.add(new GroupedLabelValue(item.getName(),"Profit",item.getSellPrice() - item.getBuyPrice()));
        });

        return result;
    }
    
    public void saveProduct(InventoryDto inventoryDto) {
        // Dto to Entity Convert
        Product product = InventoryMapper.toEntity(inventoryDto);
        product.setVendor(vendorService.findVendorById(inventoryDto.getVendorId()));
        product.setCategory(productCategoryService.findProductCategoryById(inventoryDto.getCategoryId()));

        // Save the Customer
        productDao.save(product);
    }

    public void updateProductQtyAdjust(Long id,Long qty){
        Product product = findProductById(id);
        product.setCount(product.getCount()-qty);
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
