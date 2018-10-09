package com.pexapark.windfarm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WindFarmController {

    @RequestMapping(method = RequestMethod.GET, value = "/capacity-factor")
    public String getCapacityFactor(@RequestParam(value = "startDate", required = false) String startDate,
                                    @RequestParam(value = "endDate", required = false) String endDate) {
        return "";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/produced")
    public String getEnergyProducedForPeriod(@RequestParam(value = "startDate", required = false) String startDate,
                                             @RequestParam(value = "endDate", required = false) String endDate) {
        return "";
    }
}
