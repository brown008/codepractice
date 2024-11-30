package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dao.FixedChargesRepository;
import com.example.employeemanagementsystem.dto.FixedChargesBean;
import com.example.employeemanagementsystem.entity.FixedCharges;
import com.example.employeemanagementsystem.service.FixedChargesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;

@Service
public class FixedChargesServiceImpl implements FixedChargesService {

    @Resource
    private FixedChargesRepository fixedChargesRepository;

    /**
     * 固定开支serviceImpl
     * @param fixedChargesBean
     */
    @Override
    public void saveFixedCharges(FixedChargesBean fixedChargesBean) {
        FixedCharges fixedCharges = new FixedCharges();
        fixedCharges.setStart_date(Date.valueOf(fixedChargesBean.getStartDate()));
        fixedCharges.setEnd_date(Date.valueOf(fixedChargesBean.getEndDate()));
        fixedCharges.setElectricity(BigDecimal.valueOf(Long.valueOf(fixedChargesBean.getElectricity())));
        fixedCharges.setWater(BigDecimal.valueOf(Long.valueOf(fixedChargesBean.getWater())));
        fixedCharges.setGas(BigDecimal.valueOf(Long.valueOf(fixedChargesBean.getGas())));
        fixedCharges.setInternet(BigDecimal.valueOf(Long.valueOf(fixedChargesBean.getInternet())));
        fixedCharges.setRent(BigDecimal.valueOf(Long.valueOf(fixedChargesBean.getRent())));
        fixedCharges.setMobile(BigDecimal.valueOf(Long.valueOf(fixedChargesBean.getMobile())));
        fixedChargesRepository.save(fixedCharges);
    }
}
