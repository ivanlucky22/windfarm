package com.pexapark.windfarm.service.impl;

import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindFarmRepository;
import com.pexapark.windfarm.service.WindFarmService;
import com.pexapark.windfarm.util.DatesUtil;
import com.pexapark.windfarm.vo.ElectricityProductionAggregatedPerFarmAndDateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WindFarmServiceImpl implements WindFarmService {

    private final ElectricityProductionRepository electricityProductionRepository;
    private final WindFarmRepository windFarmRepository;

    @Autowired
    public WindFarmServiceImpl(final ElectricityProductionRepository electricityProductionRepository,
                               final WindFarmRepository windFarmRepository) {
        this.electricityProductionRepository = electricityProductionRepository;
        this.windFarmRepository = windFarmRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ElectricityProductionAggregatedPerFarmAndDateVO> findCapacityFactorForRange(final Long winFarmId, final Integer startDate, final Integer endDate) {
        final Optional<WindFarm> farm = windFarmRepository.findById(winFarmId);
        farm.orElseThrow(() -> new IllegalStateException("No Farm found for Id " + winFarmId));

        final WindFarm windFarm = farm.get();
        final List<ElectricityProductionAggregatedPerFarmAndDateVO> producedAggregatedForRange = electricityProductionRepository.findProducedAggregatedByDay(windFarm, startDate, endDate);
        return producedAggregatedForRange
                .stream()
                .peek(dateToValueVo -> {
                            // Substitute VO's functionValue (sum) onto calculated capacity factor
                            final BigDecimal functionValue = dateToValueVo.getFunctionValue();
                            if (functionValue != null) {
                                final BigDecimal hoursInDay = new BigDecimal(DatesUtil.getHoursInDay(dateToValueVo.getDate(), windFarm.getTimezone()));
                                // Rounding mode is a subject to discuss with BA
                                final BigDecimal capacityFactor = functionValue.divide(windFarm.getHourlyCapacity().multiply(hoursInDay), 3, RoundingMode.HALF_EVEN).stripTrailingZeros();
                                dateToValueVo.setFunctionValue(capacityFactor);
                            }
                        }
                ).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ElectricityProductionAggregatedPerFarmAndDateVO> findElectricityProducedForRange(final Long winFarmId, final Integer startDate, final Integer endDate) {

        final Optional<WindFarm> farm = windFarmRepository.findById(winFarmId);
        farm.orElseThrow(() -> new IllegalStateException("No Farm found for Id " + winFarmId));

        return electricityProductionRepository.findProducedAggregatedByDay(farm.get(), startDate, endDate);
    }

}
