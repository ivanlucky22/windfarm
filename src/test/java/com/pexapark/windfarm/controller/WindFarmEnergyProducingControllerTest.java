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
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static com.pexapark.windfarm.util.Utils.getSecondsFromHour;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WindFarmEnergyProducingControllerTest {

    @Autowired
    private WindFarmEnergyProducingController controller;
    @Autowired
    private ElectricityProductionRepository electrProductionRepo;
    @Autowired
    private WindFarmRepository windFarmRepository;
    private WindFarm persistedFarm;

    @Before
    public void setUpTest() {
        final WindFarm farm = new WindFarm(new BigDecimal(10), ZoneOffset.systemDefault().getId());
        persistedFarm = windFarmRepository.save(farm);
    }

    @After
    public void tierDownTest() {
        electrProductionRepo.deleteAll();
        windFarmRepository.deleteAll();
    }

    @Test
    public void testEnergyProducedWithRangeShouldSucceed() {
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181128, getSecondsFromHour(0), new BigDecimal(5)));
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181128, getSecondsFromHour(1), new BigDecimal(6.5)));
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181129, getSecondsFromHour(2), new BigDecimal(7)));
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181129, getSecondsFromHour(3), new BigDecimal(8.5)));

        final List<ValuePerDateVO> actual = controller.getEnergyProducedForPeriod(20181128, 20181129, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(20181128, new BigDecimal(11.5)),
                new ValuePerDateVO(20181129, new BigDecimal(15.5)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEnergyProducedForSingleDayShouldSucceed() {
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181028, getSecondsFromHour(0), new BigDecimal(5)));
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181028, getSecondsFromHour(1), new BigDecimal(6.5)));

        final List<ValuePerDateVO> actual = controller.getEnergyProducedForPeriod(20181028, 20181028, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(20181028, new BigDecimal(11.5)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEnergyProducedForDayWithoutDataShouldReturnEmptyList() {
        final List<ValuePerDateVO> actual = controller.getEnergyProducedForPeriod(20111111, 20111111, persistedFarm.getId());
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void testEnergyProducedWithoutDateParametersShouldReturnProductionForToday() {
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, DatesUtil.getTodaysDateId(), getSecondsFromHour(1), new BigDecimal(1.2)));

        final List<ValuePerDateVO> actual = controller.getEnergyProducedForPeriod(null, null, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(DatesUtil.getTodaysDateId(), new BigDecimal(1.2)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEnergyProducedForDayWithNullableProductionShouldReturnResults() {
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20180930, getSecondsFromHour(0), null));

        final List<ValuePerDateVO> actualList = controller.getEnergyProducedForPeriod(20180930, 20180930, persistedFarm.getId());
        ValuePerDateVO expectedResult = new ValuePerDateVO(20180930, null);
        ValuePerDateVO actual = actualList.get(0);

        Assert.assertNull(actual.getCapacityFactor());
        Assert.assertEquals(expectedResult.getDate(), actual.getDate());
    }

    @Test
    public void testEnergyProducedForDayWithZeroProductionShouldReturnZero() {
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181001, getSecondsFromHour(0), new BigDecimal(0)));

        final List<ValuePerDateVO> actual = controller.getEnergyProducedForPeriod(20181001, 20181001, persistedFarm.getId());
        final ArrayList<ValuePerDateVO> expected = Lists.newArrayList(
                new ValuePerDateVO(20181001, new BigDecimal(0)));

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCapacityFactorWithWrongPeriodOfTImeShouldThrowException() {
        controller.getEnergyProducedForPeriod(20181002, 20181001, persistedFarm.getId());
    }
}