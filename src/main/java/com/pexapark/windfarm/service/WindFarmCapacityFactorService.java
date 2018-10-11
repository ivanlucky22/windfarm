package com.pexapark.windfarm.service;

import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.vo.CapacityFactorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WindFarmCapacityFactorService {

    private ElectricityProductionRepository electricityProductionRepository;

    @Autowired
    public WindFarmCapacityFactorService(final ElectricityProductionRepository electricityProductionRepository) {
        this.electricityProductionRepository = electricityProductionRepository;
    }

    public List<CapacityFactorVO> findCapacityFactorForRange(final Integer startDate, final Integer endDate) {
        return electricityProductionRepository.findProducedAggregatedByDate(startDate, endDate);
    }

}
