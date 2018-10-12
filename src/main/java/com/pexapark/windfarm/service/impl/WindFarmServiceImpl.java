package com.pexapark.windfarm.service.impl;

import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindowFarmRepository;
import com.pexapark.windfarm.service.WindFarmService;
import com.pexapark.windfarm.vo.ElectricityProductionAggregatedPerFarmAndDateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WindFarmServiceImpl implements WindFarmService {

    private final ElectricityProductionRepository electricityProductionRepository;
    private final WindowFarmRepository windowFarmRepository;

    @Autowired
    public WindFarmServiceImpl(final ElectricityProductionRepository electricityProductionRepository,
                               final WindowFarmRepository windowFarmRepository) {
        this.electricityProductionRepository = electricityProductionRepository;
        this.windowFarmRepository = windowFarmRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ElectricityProductionAggregatedPerFarmAndDateVO> findCapacityFactorForRange(final Long winFarmId, final Integer startDate, final Integer endDate) {
        final Optional<WindFarm> farm = windowFarmRepository.findById(winFarmId);
        farm.orElseThrow(() -> new IllegalStateException("No Farm found for Id " + winFarmId));

        final WindFarm windFarm = farm.get();
        final List<ElectricityProductionAggregatedPerFarmAndDateVO> producedAggregatedForRange = electricityProductionRepository.findProducedAggregatedForRange(windFarm, startDate, endDate);
        return producedAggregatedForRange
                .stream()
                .map(capacityFactorVO -> {
                            capacityFactorVO.setValue(capacityFactorVO.getValue().divide(windFarm.getCapacity()));
                            return capacityFactorVO;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public List<ElectricityProductionAggregatedPerFarmAndDateVO> findElectricityProducedForRange(final Long winFarmId, final Integer startDate, final Integer endDate) {

        final Optional<WindFarm> farm = windowFarmRepository.findById(winFarmId);
        farm.orElseThrow(() -> new IllegalStateException("No Farm found for Id " + winFarmId));

        return electricityProductionRepository.findProducedAggregatedForRange(farm.get(), startDate, endDate);
    }

}
