package com.pexapark.windfarm.controller;

import com.google.common.collect.Lists;
import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindowFarmRepository;
import com.pexapark.windfarm.util.DatesUtil;
import com.pexapark.windfarm.vo.ValuePerDateVO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

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
    }

    @Test
    public void testCapacityFactorWithRangeShouldSucceed() {
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180928, getSecondsFromHour(0), new BigDecimal(5)));
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180928, getSecondsFromHour(1), new BigDecimal(6)));
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180929, getSecondsFromHour(0), new BigDecimal(7)));
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180929, getSecondsFromHour(1), new BigDecimal(8)));

        final List<ValuePerDateVO> actual = controller.getCapacityFactor(20180928, 20180929, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(20180928, new BigDecimal(0.046)),
                new ValuePerDateVO(20180929, new BigDecimal(0.062)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorForSingleDayShouldSucceed() {
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180928, getSecondsFromHour(0), new BigDecimal(5)));
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180928, getSecondsFromHour(1), new BigDecimal(6)));

        final List<ValuePerDateVO> actual = controller.getCapacityFactor(20180928, 20180928, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(20180928, new BigDecimal(0.046)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorForSingleDayWithNullableProductionShouldSucceed() {
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180928, getSecondsFromHour(0), new BigDecimal(5)));
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180928, getSecondsFromHour(1), new BigDecimal(6)));
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180928, getSecondsFromHour(1), null));

        final List<ValuePerDateVO> actual = controller.getCapacityFactor(20180928, 20180928, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(20180928, new BigDecimal(0.046)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorForDayWithoutDataShouldReturnEmptyList() {
        final List<ValuePerDateVO> actual = controller.getCapacityFactor(20111111, 20111111, persistedFarm.getId());
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void testCapacityFactorWithoutDateParametersShouldReturnProductionForToday() {
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, DatesUtil.getTodaysDateId(), getSecondsFromHour(1), new BigDecimal(1.2)));

        final List<ValuePerDateVO> actual = controller.getCapacityFactor(null, null, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(DatesUtil.getTodaysDateId(), new BigDecimal(0.005)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorForDayWithNullableProductionShouldReturnResults() {
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180930, getSecondsFromHour(0), null));

        final List<ValuePerDateVO> actualList = controller.getCapacityFactor(20180930, 20180930, persistedFarm.getId());
        ValuePerDateVO expectedResult = new ValuePerDateVO(20180930, null);
        ValuePerDateVO actual = actualList.get(0);

        Assert.assertNull(actual.getCapacityFactor());
        Assert.assertEquals(expectedResult.getDate(), actual.getDate());
    }

    @Test
    public void testCapacityFactorForDayWithZeroProductionShouldReturnZero() {
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20181001, getSecondsFromHour(0), new BigDecimal(0)));

        final List<ValuePerDateVO> actual = controller.getCapacityFactor(20181001, 20181001, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(20181001, new BigDecimal(0)));

        Assert.assertEquals(expected, actual);
    }

    /**
     * Returns representation of hour in seconds.
     * Ex 1 am is 3600 seconds
     *
     * @param hours time in hours to be converted to seconds
     * @return
     */
    private int getSecondsFromHour(final int hours) {
        LocalDateTime midnight = LocalDate.of(1970, Month.JANUARY, 1).atStartOfDay();
        long seconds = midnight.plusHours(hours).toEpochSecond(ZoneOffset.UTC);
        return (int) seconds;
    }

    @After
    public void tierDownTest() {
        electricityProductionRepository.deleteAll();
        windowFarmRepository.deleteAll();
    }
}