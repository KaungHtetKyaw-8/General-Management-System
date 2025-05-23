package com.khk.mgt.service;

import com.khk.mgt.dao.PointCardCategoryDao;
import com.khk.mgt.dao.PointCardDao;
import com.khk.mgt.ds.PointCard;
import com.khk.mgt.ds.PointCardCategory;
import com.khk.mgt.dto.common.PointCardDto;
import com.khk.mgt.mapper.PointCardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class PointCardService {
    @Autowired
    private PointCardDao pointCardDao;

    @Autowired
    private PointCardCategoryDao pointCardCategoryDao;

    @Autowired
    private CustomerService customerService;

    public PointCard findById(Long id) {
        return pointCardDao.findById(id).orElse(null);
    }

    public boolean isPointCardExist(Long id){
        return pointCardDao.findById(id).isEmpty();
    }

    public boolean isMatchWithCustomer(Long pointCardId,Long customerId){
        PointCard pointCard = pointCardDao.findById(pointCardId).orElse(null);

        if(pointCard != null){
            return pointCard.getCustomer().getId().compareTo(customerId) == 0;
        }

        return false;
    }

    public PointCardCategory findCategoryById(Long id) {
        return pointCardCategoryDao.findById(id).orElse(null);
    }

    public PointCardDto getPointCardById(Long id) {
        PointCard findPointCard = findById(id);

        if (findPointCard != null) {
            return PointCardMapper.toDto(findPointCard);
        }else {
            return null;
        }
    }

    public void savePointCard(PointCardDto pointCardDto){
        PointCard pointCard = new PointCard();

        pointCard.setRegistrationDate(Date.valueOf(LocalDate.now()));
        pointCard.setPoints(0L);
        pointCard.setCategory(pointCardCategoryDao.getById(pointCardDto.getPointCardCategory()));
        pointCard.setCustomer(customerService.findCustomerById(pointCardDto.getCustomerId()));

        pointCardDao.save(pointCard);
    }

    public void updatePointCardCount(Long pointCardId,Long Count){
        PointCard pointCard = findById(pointCardId);

        if(pointCard != null){
            pointCard.setPoints(pointCard.getPoints() + Count);
        }
    }

    public void updatePointCard(PointCardDto pointCardDto){
        PointCard pointCard = findById(pointCardDto.getPointCardId());

        pointCard.setCategory(pointCardCategoryDao.getById(pointCardDto.getPointCardCategory()));

        pointCardDao.save(pointCard);
    }

    public void deletePointCard(PointCardDto pointCardDto){
        pointCardDao.delete(findById(pointCardDto.getPointCardId()));
    }

}
