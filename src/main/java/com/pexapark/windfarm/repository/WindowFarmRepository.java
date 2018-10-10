package com.pexapark.windfarm.repository;

import com.pexapark.windfarm.entity.WindFarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WindowFarmRepository extends JpaRepository<WindFarm, Long> {

}
