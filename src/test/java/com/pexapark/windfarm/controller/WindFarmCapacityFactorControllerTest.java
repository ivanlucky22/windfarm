package com.pexapark.windfarm.controller;

import com.google.common.collect.Lists;
import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindowFarmRepository;
import com.pexapark.windfarm.util.DatesUtil;
import com.pexapark.windfarm.vo.FarmValuePerDateVO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WindFarmCapacityFactorControllerTest {

    @Autowired
    private WindFarmCapacityFactorController controller;
    @Autowired
    private ElectricityProductionRepository electricityProductionRepository;
    @Autowired
    private WindowFarmRepository windowFarmRepository;
    private WindFarm persistedFarm;

    @Before
    public void setUpTest() {
        final WindFarm farm = new WindFarm(new BigDecimal(10), ZoneOffset.systemDefault().getId());
        persistedFarm = windowFarmRepository.save(farm);
        final Optional<WindFarm> one = windowFarmRepository.findOne(Example.of(farm));

        one.ifPresent(windFarm -> {
            electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180928, getHours(0), new BigDecimal(5)));
            electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180928, getHours(1), new BigDecimal(6)));
            electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180929, getHours(0), new BigDecimal(7)));
            electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180929, getHours(1), new BigDecimal(8)));

            electricityProductionRepository.save(new ElectricityProduction(persistedFarm, DatesUtil.getTodaysDateId(), getHours(1), new BigDecimal(1.2)));
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
        final List<FarmValuePerDateVO> actual = controller.getCapacityFactor(20180928, 20180929, persistedFarm.getId());
        final ArrayList<FarmValuePerDateVO> expected = Lists.newArrayList(
                new FarmValuePerDateVO(20180928, new BigDecimal(1.1)),
                new FarmValuePerDateVO(20180929, new BigDecimal(1.5)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorForSingleDayShouldSucceed() {
        final List<FarmValuePerDateVO> actual = controller.getCapacityFactor(20181028, 20181028, persistedFarm.getId());
        final ArrayList<FarmValuePerDateVO> expected = Lists.newArrayList(
                new FarmValuePerDateVO(20181028, new BigDecimal(1.1)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorForDayWithoutDataShouldReturnEmptyList() {
        final List<FarmValuePerDateVO> actual = controller.getCapacityFactor(20111111, 20111111, persistedFarm.getId());
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void testCapacityFactorWithoutDateParametersShouldReturnProductionForToday() {
        final List<FarmValuePerDateVO> actual = controller.getCapacityFactor(null, null, persistedFarm.getId());
        final ArrayList<FarmValuePerDateVO> expected = Lists.newArrayList(
                new FarmValuePerDateVO(DatesUtil.getTodaysDateId(), new BigDecimal(.12)));

        Assert.assertEquals(expected, actual);
    }
}