package com.pexapark.windfarm.repository;

import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.vo.CapacityFactorVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectricityProductionRepository extends JpaRepository<ElectricityProduction, Long> {

    @Query("select new com.pexapark.windfarm.vo.CapacityFactorVO(date, sum(electricityProduced)) from ElectricityProduction where date >= ?1 and date <= ?2 group by date")
    List<CapacityFactorVO> findProducedAggregatedByDate(Long startDate, Long endDate);

}
