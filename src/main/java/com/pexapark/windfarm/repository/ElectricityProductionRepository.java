package com.pexapark.windfarm.repository;

import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.vo.ElectricityProductionAggregatedPerFarmAndDateVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectricityProductionRepository extends CrudRepository<ElectricityProduction, Long> {

    @Query("select new com.pexapark.windfarm.vo.ElectricityProductionAggregatedPerFarmAndDateVO(ep.windFarm, ep.date, sum(ep.electricityProduced)) " +
            "from ElectricityProduction as ep " +
            "where ep.windFarm = ?1 and date >= ?2 and date <= ?3 " +
            "group by ep.windFarm, ep.date")
    List<ElectricityProductionAggregatedPerFarmAndDateVO> findProducedAggregatedByDay(WindFarm windFarm, Integer startDate, Integer endDate);
}
