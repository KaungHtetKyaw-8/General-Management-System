package com.khk.mgt.service;

import com.khk.mgt.dao.CustomerDao;
import com.khk.mgt.dao.PointCardCategoryDao;
import com.khk.mgt.ds.Customer;
import com.khk.mgt.ds.PointCard;
import com.khk.mgt.dto.common.CustomerDto;
import com.khk.mgt.dto.common.PointCardDto;
import com.khk.mgt.mapper.CustomerMapper;
import com.khk.mgt.mapper.PointCardMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PointCardCategoryDao pointCardCategoryDao;

    public Customer findCustomerById(Long id) {
        return customerDao.findById(id).orElse(null);
    }

    public boolean isCustomerExist(Long id) {
        return customerDao.findById(id).isEmpty();
    }

    public List<CustomerDto> getAllCustomers() {
        return customerDao.findAll()
                .stream().map(CustomerMapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(Long id) {
        return customerDao.findById(id)
                .map(CustomerMapper::toDto)
                .orElse(null);
    }

    public List<CustomerDto> searchIdOrName(String query) {
        return customerDao.searchIdOrNameByKeyword(query)
                .stream().map(CustomerMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PointCardDto> getPointCardsByCustomerId(Long id) {
        List<PointCardDto> result = new ArrayList<>();
        Customer findCustomer = customerDao.findById(id).orElse(null);

        if (findCustomer != null) {
            result = findCustomer
                    .getPointCard()
                    .stream()
                    .map(PointCardMapper::toDto)
                    .collect(Collectors.toList());
            return result;
        }else {
            return new ArrayList<>();
        }
    }

    public void updateCustomer(CustomerDto customerDto) {
        Customer existCus = customerDao.findById(customerDto.getId()).orElse(null);
        if (existCus != null) {
            Customer updateCus = CustomerMapper.toEntity(customerDto);
            BeanUtils.copyProperties(updateCus, existCus, "id", "address");

            if (updateCus.getAddress() != null && existCus.getAddress() != null) {
                BeanUtils.copyProperties(updateCus.getAddress(), existCus.getAddress(), "id");
            }

            customerDao.save(existCus);
        }
    }

    public void deleteCustomer(Long id) {
        customerDao.deleteById(id);
    }

    public void saveCustomer(CustomerDto customerDto) {
        // Dto to Entity Convert
        Customer customer = CustomerMapper.toEntity(customerDto);

        // New Point Card Get with minimum level
        PointCard pointCard = new PointCard();
        pointCard.setCategory(pointCardCategoryDao.getById(1L));
        pointCard.setRegistrationDate(Date.valueOf(LocalDate.now()));
        pointCard.setPoints(0L);

        // Customer Point Card set
        customer.setPointCard(new ArrayList<>());
        customer.addPointCard(pointCard);

        // Save the Customer
        customerDao.save(customer);
    }
}
