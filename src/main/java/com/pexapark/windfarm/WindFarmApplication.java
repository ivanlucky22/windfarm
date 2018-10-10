package com.pexapark.windfarm;

import com.pexapark.windfarm.config.WindFarmConfiguration;
import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.time.ZonedDateTime;

@SpringBootApplication
@Import({WindFarmConfiguration.class})
public class WindFarmApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WindFarmApplication.class, args);
    }

    @Autowired
    private ElectricityProductionRepository electricityProductionRepository;

    @Override
    public void run(final String... args) throws Exception {
        electricityProductionRepository.save(new ElectricityProduction(null, ZonedDateTime.now(), 30L));
    }
}
