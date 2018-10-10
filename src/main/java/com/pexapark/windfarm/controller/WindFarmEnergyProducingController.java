package com.pexapark.windfarm.controller;

import com.pexapark.windfarm.common.Constants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WindFarmEnergyProducingController {

    @RequestMapping(method = RequestMethod.GET, value = "/produced")
    @ApiOperation(value = "Returns the amount of electricity produced for defined period of time in days",
            notes = "If any parameter of startDate and endDate is not provided, by default today's date is used.\n" +
                    "If you want to query data from some certain date until today please provide only startDate parameter.\n" +
                    "If you want to query data for today - you can ignore parameters.")
    public String getEnergyProducedForPeriod(@ApiParam("Start period date in format " + Constants.DEFAULT_DATE_FORMAT + ". Current date is used if nothing specified.")
                                             @RequestParam(value = "startDate", required = false) String startDate,
                                             @ApiParam("End period date in format " + Constants.DEFAULT_DATE_FORMAT + ". Current date is used if nothing specified.")
                                             @RequestParam(value = "endDate", required = false) String endDate) {
        return "";
    }
}
