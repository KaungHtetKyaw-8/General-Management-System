package com.khk.mgt.service;

import com.khk.mgt.dao.PointCardCategoryDao;
import com.khk.mgt.dao.VendorDao;
import com.khk.mgt.ds.Vendor;
import com.khk.mgt.dto.common.SelectionDto;
import com.khk.mgt.dto.common.VendorDto;
import com.khk.mgt.mapper.VendorMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class VendorService {
    @Autowired
    private VendorDao vendorDao;

    @Autowired
    private PointCardCategoryDao pointCardCategoryDao;

    public Vendor findVendorById(Long id) {
        return vendorDao.findById(id).orElse(null);
    }

    public boolean isVendorExist(Long id) {
        return vendorDao.findById(id).isEmpty();
    }

    public List<VendorDto> getAllVendor() {
        List<Vendor> vendorList = vendorDao.findAll();

        if (vendorList.isEmpty()) {
            return null;
        }

        return vendorList
                .stream()
                .map(VendorMapper::toDto)
                .collect(Collectors.toList());
    }

    public VendorDto getVendorById(Long id) {
        return vendorDao.findById(id)
                .map(VendorMapper::toDto)
                .orElse(null);
    }

    public List<SelectionDto> getByCompanyName(String companyName) {
        return vendorDao.findByCompanyName(companyName).stream()
                .map(vendor -> new SelectionDto(vendor.getId(),vendor.getFirstName() + " " + vendor.getLastName()))
                .collect(Collectors.toList());
    }

    
    public void saveVendor(VendorDto vendorDto) {
        // Dto to Entity Convert
        Vendor vendor = VendorMapper.toEntity(vendorDto);

        // Save the Customer
        vendorDao.save(vendor);
    }

    public void updateVendor(VendorDto vendorDto) {
        Vendor existVendor = findVendorById(vendorDto.getId());
        if (existVendor != null) {
            Vendor updateVendor = VendorMapper.toEntity(vendorDto);
            BeanUtils.copyProperties(updateVendor, existVendor, "id", "address");

            if (updateVendor.getAddress() != null && existVendor.getAddress() != null) {
                BeanUtils.copyProperties(updateVendor.getAddress(), existVendor.getAddress(), "id");
            }

            vendorDao.save(existVendor);
        }
    }

    public void deleteVendor(Long id) {
        vendorDao.deleteById(id);
    }
}
