package com.pexapark.windfarm.controller;

import com.google.common.collect.Lists;
import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindowFarmRepository;
import com.pexapark.windfarm.util.DatesUtil;
import com.pexapark.windfarm.vo.CapacityFactorVO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
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

        one.ifPresent(windFarm -> {
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181028, getHours(0), new BigDecimal(5)));
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181028, getHours(1), new BigDecimal(6)));
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181029, getHours(0), new BigDecimal(7)));
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, 20181029, getHours(1), new BigDecimal(8)));

            electricityProductionRepository.save(new ElectricityProduction(savedFarm, DatesUtil.getTodaysDateId(), getHours(1), new BigDecimal(10)));
        });
    }

    private int getHours(final int hours) {
        LocalDateTime midnight = LocalDate.of(1970, Month.JANUARY, 1).atStartOfDay();
        long seconds = midnight.plusHours(hours).toEpochSecond(ZoneOffset.UTC);
        return (int) seconds;
    }

    @After
    public void tierDownTest() {
        electricityProductionRepository.deleteAll();
        windowFarmRepository.deleteAll();
    }

    @Test
    public void testCapacityFactorWithRangeShouldSucceed() {
        final List<CapacityFactorVO> actual = windFarmCapacityFactorController.getCapacityFactor("20181028", "20181029");
        final ArrayList<CapacityFactorVO> expected = Lists.newArrayList(
                new CapacityFactorVO(20181028, new BigDecimal(11)),
                new CapacityFactorVO(20181029, new BigDecimal(15)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorForSingleDayShouldSucceed() {
        final List<CapacityFactorVO> actual = windFarmCapacityFactorController.getCapacityFactor("20181028", "20181028");
        final ArrayList<CapacityFactorVO> expected = Lists.newArrayList(
                new CapacityFactorVO(20181028, new BigDecimal(11)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorForDayWithoutDataShouldReturnEmptyList() {
        final List<CapacityFactorVO> actual = windFarmCapacityFactorController.getCapacityFactor("20111111", "20111111");
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void testCapacityFactorWithoutParametersShouldReturnProductionForToday() {
        final List<CapacityFactorVO> actual = windFarmCapacityFactorController.getCapacityFactor(null, null);
        final ArrayList<CapacityFactorVO> expected = Lists.newArrayList(
                new CapacityFactorVO(DatesUtil.getTodaysDateId(), new BigDecimal(10)));

        Assert.assertEquals(expected, actual);
    }
}