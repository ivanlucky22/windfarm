package com.pexapark.windfarm.repository;

import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.vo.CapacityFactorVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectricityProductionRepository extends CrudRepository<ElectricityProduction, Long> {

    @Query("select new com.pexapark.windfarm.vo.CapacityFactorVO(windFarm, date, sum(electricityProduced)) from ElectricityProduction where date >= ?1 and date <= ?2 group by date and windFarm")
    List<CapacityFactorVO> findProducedAggregatedForRange(WindFarm windFarm, Integer startDate, Integer endDate);
}
