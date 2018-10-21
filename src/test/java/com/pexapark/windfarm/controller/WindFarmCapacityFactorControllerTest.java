package com.pexapark.windfarm.controller;

import com.google.common.collect.Lists;
import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindFarmRepository;
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

import static com.pexapark.windfarm.util.Utils.getSecondsFromHour;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WindFarmCapacityFactorControllerTest {

    @Autowired
    private WindFarmCapacityFactorController controller;
    @Autowired
    private ElectricityProductionRepository electricityProductionRepository;
    @Autowired
    private WindFarmRepository windFarmRepository;
    private WindFarm persistedFarm;

    @Before
    public void setUpTest() {
        final WindFarm farm = new WindFarm(new BigDecimal(10), ZoneOffset.systemDefault().getId());
        persistedFarm = windFarmRepository.save(farm);
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
    public void testCapacityFactorForSingleOrdinalDayShouldSucceed() {
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180928, getSecondsFromHour(0), new BigDecimal(5)));
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180928, getSecondsFromHour(1), new BigDecimal(6)));

        final List<ValuePerDateVO> actual = controller.getCapacityFactor(20180928, 20180928, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(20180928, new BigDecimal(0.046)));
        // 0.046 = (5+6)/(10*24) where 10 is max capacity per hour and 24 hours in this day

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorFor25houredDayShouldSucceed() {
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20181028, getSecondsFromHour(0), new BigDecimal(5)));
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20181028, getSecondsFromHour(1), new BigDecimal(6)));

        final List<ValuePerDateVO> actual = controller.getCapacityFactor(20181028, 20181028, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(20181028, new BigDecimal(0.044)));
        // 0.044 = (5+6)/(10*25) where 10 is max capacity per hour and 25 hours in this day

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorFor23houredDayShouldSucceed() {
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180325, getSecondsFromHour(0), new BigDecimal(5)));
        electricityProductionRepository.save(new ElectricityProduction(persistedFarm, 20180325, getSecondsFromHour(1), new BigDecimal(6)));

        final List<ValuePerDateVO> actual = controller.getCapacityFactor(20180325, 20180325, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(20180325, new BigDecimal(0.048)));
        // 0.048 = (5+6)/(10*23) where 10 is max capacity per hour and 23 hours in this day

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

    @Test(expected = IllegalArgumentException.class)
    public void testCapacityFactorWithWrongPeriodOfTImeShouldThrowException() {
        controller.getCapacityFactor(20181002, 20181001, persistedFarm.getId());
    }

    @After
    public void tierDownTest() {
        electricityProductionRepository.deleteAll();
        windFarmRepository.deleteAll();
    }
}