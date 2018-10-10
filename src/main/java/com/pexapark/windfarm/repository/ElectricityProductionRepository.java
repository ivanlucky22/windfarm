package com.pexapark.windfarm.repository;

import com.pexapark.windfarm.entity.ElectricityProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ElectricityProductionRepository extends JpaRepository<ElectricityProduction, Long> {

    List<ElectricityProduction> findAllByTimestampLessThanEqualAndTimestampGreaterThanEqual(ZonedDateTime endDate, ZonedDateTime startDate);

}
