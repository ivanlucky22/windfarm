package com.pexapark.windfarm.service.impl;

import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindowFarmRepository;
import com.pexapark.windfarm.service.WindFarmService;
import com.pexapark.windfarm.vo.CapacityFactorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WindFarmServiceImpl implements WindFarmService {

    private final ElectricityProductionRepository electricityProductionRepository;
    private WindowFarmRepository windowFarmRepository;

    @Autowired
    public WindFarmServiceImpl(final ElectricityProductionRepository electricityProductionRepository,
                               final WindowFarmRepository windowFarmRepository) {
        this.electricityProductionRepository = electricityProductionRepository;
        this.windowFarmRepository = windowFarmRepository;
    }

    @Override
    public List<CapacityFactorVO> findCapacityFactorForRange(final Long winFarmId, final Integer startDate, final Integer endDate) {
        Optional<WindFarm> farm = windowFarmRepository.findById(winFarmId);
        farm.orElseThrow(() -> new IllegalStateException("No Farm found for Id " + winFarmId));

        WindFarm windFarm = farm.get();
        List<CapacityFactorVO> producedAggregatedForRange = electricityProductionRepository.findProducedAggregatedForRange(windFarm, startDate, endDate);
        return producedAggregatedForRange.stream().map(capacityFactorVO -> {
                    capacityFactorVO.setCapacityFactor(capacityFactorVO.getCapacityFactor().divide(windFarm.getCapacity()));
                    return capacityFactorVO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<CapacityFactorVO> findElectricityProducedForRange(final Long winFarmId, final Integer startDate, final Integer endDate) {

        Optional<WindFarm> farm = windowFarmRepository.findById(winFarmId);
        farm.orElseThrow(() -> new IllegalStateException("No Farm found for Id " + winFarmId));

        return electricityProductionRepository.findProducedAggregatedForRange(farm.get(), startDate, endDate);
    }

}
