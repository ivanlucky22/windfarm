package com.pexapark.windfarm.controller;

import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindFarmRepository;
import com.pexapark.windfarm.util.DatesUtil;
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

import static com.pexapark.windfarm.util.Utils.getSecondsFromHour;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WindFarmEnergyProducingSumControllerTest {

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
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181028, getSecondsFromHour(0), new BigDecimal(5)));
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181028, getSecondsFromHour(1), new BigDecimal(6.5)));
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181029, getSecondsFromHour(0), new BigDecimal(7)));
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181029, getSecondsFromHour(1), new BigDecimal(8.5)));

        final BigDecimal actual = controller.getEnergyProducedSumForPeriod(20181028, 20181029, persistedFarm.getId());
        final BigDecimal expected = new BigDecimal(27);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEnergyProducedForSingleDayShouldSucceed() {
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181028, getSecondsFromHour(0), new BigDecimal(5)));
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181028, getSecondsFromHour(1), new BigDecimal(6.5)));

        final BigDecimal actual = controller.getEnergyProducedSumForPeriod(20181028, 20181028, persistedFarm.getId());
        final BigDecimal expected = new BigDecimal(11.5);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEnergyProducedForDayWithoutDataShouldReturnEmptyList() {
        final BigDecimal actual = controller.getEnergyProducedSumForPeriod(20111111, 20111111, persistedFarm.getId());
        final BigDecimal expected = new BigDecimal(0);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testEnergyProducedWithoutDateParametersShouldReturnProductionForToday() {
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, DatesUtil.getTodaysDateId(), getSecondsFromHour(1), new BigDecimal(1.5)));

        final BigDecimal actual = controller.getEnergyProducedSumForPeriod(null, null, persistedFarm.getId());
        final BigDecimal expected = new BigDecimal(1.5);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEnergyProducedForDayWithNullableProductionShouldReturnResults() {
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20180930, getSecondsFromHour(0), null));

        final BigDecimal actual = controller.getEnergyProducedSumForPeriod(20180930, 20180930, persistedFarm.getId());
        final BigDecimal expected = new BigDecimal(0);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEnergyProducedForDayWithZeroProductionShouldReturnZero() {
        electrProductionRepo.save(new ElectricityProduction(persistedFarm, 20181001, getSecondsFromHour(0), new BigDecimal(0)));

        final BigDecimal actual = controller.getEnergyProducedSumForPeriod(20181001, 20181001, persistedFarm.getId());
        final BigDecimal expected = new BigDecimal(0);

        Assert.assertEquals(expected, actual);
    }
}