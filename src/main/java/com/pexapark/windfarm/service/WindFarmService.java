package com.pexapark.windfarm.service;

import com.pexapark.windfarm.vo.CapacityFactorVO;

import java.util.List;

/**
 * Service that contains methods for communication with a wind farm
 */
public interface WindFarmService {

    /**
     * Calculates the capacity factor for a wind farm per day for certain range of days.
     * The capacity factor for any given period of time is the actual amount of electricity produced,
     * divided by the maximum possible amount of electricity that could have been produced
     * if the farm would have run at full capacity.
     *
     * @param winFarmId a wind farm id
     * @param startDate is the range start date id in format yyyyMMdd
     * @param endDate   is the range end date id in format yyyyMMdd
     * @return the list of {@link CapacityFactorVO} instances that contains date and capacity factor
     */
    List<CapacityFactorVO> findCapacityFactorForRange( final Long winFarmId, Integer startDate, Integer endDate);

    /**
     * Calculates the amount of electricity produced per date for given time range
     *
     * @param winFarmId a wind farm id
     * @param startDate is the range start date id in format yyyyMMdd
     * @param endDate   is the range end date id in format yyyyMMdd
     * @return the list of {@link CapacityFactorVO} instances that contains date and capacity factor
     */
    List<CapacityFactorVO> findElectricityProducedForRange(final Long winFarmId, final Integer startDate, final Integer endDate);
}
