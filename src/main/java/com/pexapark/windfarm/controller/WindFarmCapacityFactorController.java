package com.pexapark.windfarm.controller;

import com.google.common.collect.Lists;
import com.pexapark.windfarm.common.Constants;
import com.pexapark.windfarm.vo.CapacityFactorVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WindFarmCapacityFactorController {

    @RequestMapping(method = RequestMethod.GET, value = "/capacity-factor")
    @ApiOperation(value = "Returns capacity factor for any period of time in days",
            notes = "If any parameter of startDate and endDate is not provided, by default today's date is used.\n" +
                    "If you want to query data from some certain date until today please provide only startDate parameter.\n" +
                    "If you want to query data for today - you can ignore parameters.")
    public List<CapacityFactorVO> getCapacityFactor(@ApiParam("Start period date in format " + Constants.DEFAULT_FORMAT + ". Current date is used if nothing specified.")
                                    @RequestParam(value = "startDate", required = false) String startDate,
                                                    @ApiParam("End period date in format " + Constants.DEFAULT_FORMAT + ". Current date is used if nothing specified.")
                                    @RequestParam(value = "endDate", required = false) String endDate) {
        return Lists.newArrayList(
                new CapacityFactorVO("08/11/2018", new BigDecimal(0.9)),
                new CapacityFactorVO("09/11/2018", new BigDecimal(0.9)));
    }
}
