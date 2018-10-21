package com.pexapark.windfarm;

import com.pexapark.windfarm.config.WindFarmConfiguration;
import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindFarmRepository;
import com.pexapark.windfarm.util.DatesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

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
    public void run(String... args) {
        // Initial data for quick testing
        final WindFarm farm = new WindFarm(new BigDecimal(10), ZoneOffset.systemDefault().getId());
        WindFarm savedFarm = windFarmRepository.save(farm);

        electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181028, getSecondsFromHour(0), new BigDecimal(5)));
        electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181028, getSecondsFromHour(1), new BigDecimal(6)));
        electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181029, getSecondsFromHour(0), new BigDecimal(7)));
        electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181029, getSecondsFromHour(1), new BigDecimal(8)));
        electricityProductionRepository.save(new ElectricityProduction(savedFarm, DatesUtil.getTodaysDateId(), getSecondsFromHour(1), new BigDecimal(10)));

    }

    public static int getSecondsFromHour(final int hours) {
        LocalDateTime midnight = LocalDate.of(1970, Month.JANUARY, 1).atStartOfDay();
        long seconds = midnight.plusHours(hours).toEpochSecond(ZoneOffset.UTC);
        return (int) seconds;
    }
}
