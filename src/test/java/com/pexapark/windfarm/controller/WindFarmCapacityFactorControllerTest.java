package com.pexapark.windfarm.controller;

import com.google.common.collect.Lists;
import com.pexapark.windfarm.vo.CapacityFactorVO;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WindFarmCapacityFactorControllerTest {

    @Autowired
    private WindFarmCapacityFactorController windFarmCapacityFactorController;

    @Test
    public void testCapacityFactorWithStartAndEnd() {
        final List<CapacityFactorVO> actual = windFarmCapacityFactorController.getCapacityFactor("08/11/2018", "09/11/2018");
        final ArrayList<CapacityFactorVO> expected = Lists.newArrayList(
                new CapacityFactorVO("08/11/2018", new BigDecimal(0.9)),
                new CapacityFactorVO("09/11/2018", new BigDecimal(0.8)));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCapacityFactorWithStartOnly() {
    }

    @Test
    public void testCapacityFactorWithEndOnly() {
    }
}