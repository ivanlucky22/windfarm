package com.pexapark.windfarm;

import com.pexapark.windfarm.config.WindFarmConfiguration;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindFarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.time.*;

@SpringBootApplication
@Import({WindFarmConfiguration.class})
public class WindFarmApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WindFarmApplication.class, args);
    }

    @Autowired
    private ElectricityProductionRepository electricityProductionRepository;
    @Autowired
    private WindFarmRepository windFarmRepository;

    @Override
    public void run(String... args) throws Exception {
//        final WindFarm farm = new WindFarm(new BigDecimal(10), ZoneOffset.systemDefault().getId());
//        WindFarm savedFarm = windowFarmRepository.save(farm);
//        final Optional<WindFarm> one = windowFarmRepository.findOne(Example.of(farm));
//
//        one.ifPresent(windFarm -> {
//            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181028, getHours(0), new BigDecimal(5)));
//            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181028, getHours(1), new BigDecimal(6)));
//            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181029, getHours(0), new BigDecimal(7)));
//            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181029, getHours(1), new BigDecimal(8)));
//
//            electricityProductionRepository.save(new ElectricityProduction(savedFarm, DatesUtil.getTodaysDateId(), getHours(1), new BigDecimal(10)));
//        });
    }
}
