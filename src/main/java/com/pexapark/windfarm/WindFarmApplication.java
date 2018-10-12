package com.pexapark.windfarm;

import com.pexapark.windfarm.config.WindFarmConfiguration;
import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindowFarmRepository;
import com.pexapark.windfarm.util.DatesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;

import java.math.BigDecimal;
import java.time.*;
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
    private int getHours(final int hours) {
        LocalDateTime midnight = LocalDate.of(1970, Month.JANUARY, 1).atStartOfDay();
        long seconds = midnight.plusHours(hours).toEpochSecond(ZoneOffset.UTC);
        return (int) seconds;
    }
}
