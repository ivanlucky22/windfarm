package com.pexapark.windfarm.controller;

import com.google.common.collect.Lists;
import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindowFarmRepository;
import com.pexapark.windfarm.vo.CapacityFactorVO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WindFarmCapacityFactorControllerTest {

    @Autowired
    private WindFarmCapacityFactorController windFarmCapacityFactorController;
    @Autowired
    private ElectricityProductionRepository electricityProductionRepository;
    @Autowired
    private WindowFarmRepository windowFarmRepository;

    @Before
    public void setUpTest() {
        final WindFarm farm = new WindFarm(new BigDecimal(10), ZoneOffset.systemDefault().getId());
        final WindFarm savedFarm = windowFarmRepository.save(farm);
        final Optional<WindFarm> one = windowFarmRepository.findOne(Example.of(farm));

//        int hours = getHours(savedFarm, 1);
//        int hours1 = getHours(savedFarm, 1);
//        int hours2 = getHours(savedFarm, 2);
//        int hours3 = getHours(savedFarm, 1);
        one.ifPresent(windFarm -> {
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181028L, 2000L, new BigDecimal(5)));
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181028L, 4000L, new BigDecimal(6)));
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181029L, 2000L, new BigDecimal(7)));
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181029L, 2000L, new BigDecimal(8)));
        });
    }

    private int getHours(final WindFarm savedFarm, final int hours) {
        LocalDateTime midnight = LocalDate.of(1970, Month.JANUARY, 1).atStartOfDay();
        long seconds = midnight.plusHours(hours).toEpochSecond(ZoneOffset.of(savedFarm.getTimezone()));
        return (int) seconds;
    }

    @After
    public void tierDownTest() {
        electricityProductionRepository.deleteAll();
        windowFarmRepository.deleteAll();
    }

    @Test
    public void testCapacityFactorWithStartAndEnd() {
        final List<CapacityFactorVO> actual = windFarmCapacityFactorController.getCapacityFactor("20181028", "20181029");
        final ArrayList<CapacityFactorVO> expected = Lists.newArrayList(
                new CapacityFactorVO(20181028L, new BigDecimal(11)),
                new CapacityFactorVO(20181029L, new BigDecimal(15)));

        Assert.assertEquals(expected, actual);
    }

}