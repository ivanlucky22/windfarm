package com.pexapark.windfarm;

import com.pexapark.windfarm.config.WindFarmConfiguration;
import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindowFarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;

import java.time.ZonedDateTime;
import java.util.Optional;

@SpringBootApplication
@Import({WindFarmConfiguration.class})
public class WindFarmApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WindFarmApplication.class, args);
    }

    @Autowired
    private ElectricityProductionRepository electricityProductionRepository;
    @Autowired
    private WindowFarmRepository windowFarmRepository;

    @Override
    public void run(String... args) throws Exception {

    }
}
