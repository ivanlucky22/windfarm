package com.pexapark.windfarm.controller;

import com.google.common.collect.Lists;
import com.pexapark.windfarm.entity.ElectricityProduction;
import com.pexapark.windfarm.entity.WindFarm;
import com.pexapark.windfarm.repository.ElectricityProductionRepository;
import com.pexapark.windfarm.repository.WindowFarmRepository;
import com.pexapark.windfarm.vo.CapacityFactorVO;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pexapark.windfarm.common.Constants.DEFAULT_DATE_FORMAT;

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
        final WindFarm farm = new WindFarm(10000L, 1L);
        final WindFarm savedFarm = windowFarmRepository.save(farm);
        final Optional<WindFarm> one = windowFarmRepository.findOne(Example.of(farm));

        final ZonedDateTime october28 = LocalDate.parse("20181028", DateTimeFormatter.BASIC_ISO_DATE).atStartOfDay(ZoneId.systemDefault());
        one.ifPresent(windFarm -> {
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, october28, 5000L));
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, october28.plusHours(1), 6000L));
            final ZonedDateTime october29 = october28.plusDays(1);
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, october29, 7000L));
            electricityProductionRepository.save(new ElectricityProduction(savedFarm, october29.plusHours(1), 8000L));
        });
    }

    @After
    public void tierDownTest() {
        windowFarmRepository.deleteAll();
        electricityProductionRepository.deleteAll();
    }

    @Test
    public void testCapacityFactorWithStartAndEnd() {
        final List<CapacityFactorVO> actual = windFarmCapacityFactorController.getCapacityFactor("08/11/2018", "09/11/2018");
        final ArrayList<CapacityFactorVO> expected = Lists.newArrayList(
                new CapacityFactorVO("28/10/2018", new BigDecimal(11000L)),
                new CapacityFactorVO("29/10/2018", new BigDecimal(15000L)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorWithStartOnly() {
    }

    @Test
    public void testCapacityFactorWithEndOnly() {
    }
}