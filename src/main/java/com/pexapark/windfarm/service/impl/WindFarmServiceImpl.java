package com.pexapark.windfarm.service.impl;

import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.service.WindFarmService;
import com.pexapark.windfarm.vo.CapacityFactorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WindFarmServiceImpl implements WindFarmService {

    private final ElectricityProductionRepository electricityProductionRepository;

    @Autowired
    public WindFarmServiceImpl(final ElectricityProductionRepository electricityProductionRepository) {
        this.electricityProductionRepository = electricityProductionRepository;
    }

    @Override
    public List<CapacityFactorVO> findCapacityFactorForRange(final Integer startDate, final Integer endDate) {
        return electricityProductionRepository.findProducedAggregatedByDate(startDate, endDate);
    }

    @Override
    public List<CapacityFactorVO> findElectricityProducedForRange(final Integer startDate, final Integer endDate) {
        return null;
    }

}
