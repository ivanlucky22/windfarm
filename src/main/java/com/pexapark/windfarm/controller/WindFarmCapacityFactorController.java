package com.pexapark.windfarm.controller;

import com.pexapark.windfarm.common.Constants;
import com.pexapark.windfarm.service.WindFarmService;
import com.pexapark.windfarm.util.DatesUtil;
import com.pexapark.windfarm.vo.ElectricityProductionAggregatedPerFarmAndDateVO;
import com.pexapark.windfarm.vo.ValuePerDateVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class WindFarmCapacityFactorController {

    private final WindFarmService windFarmService;

    @Autowired
    public WindFarmCapacityFactorController(final WindFarmService windFarmService) {
        this.windFarmService = windFarmService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/capacity-factor")
    @ApiOperation(value = "Returns capacity factor for any period of time in days",
            notes = "If any parameter of startDate and endDate is not provided, by default today's date is used.\n" +
                    "If you want to query data from some certain date until today please provide only startDate parameter.\n" +
                    "If you want to query data for today - you can ignore parameters.")
    public List<ValuePerDateVO> getCapacityFactor(@ApiParam("Start period date in format " + Constants.DEFAULT_DATE_FORMAT_TEMPLATE + ". Current date is used if nothing specified.")
                                                  @RequestParam(value = "startDate", required = false) Integer startDate,
                                                  @ApiParam("End period date in format " + Constants.DEFAULT_DATE_FORMAT_TEMPLATE + ". Current date is used if nothing specified.")
                                                  @RequestParam(value = "endDate", required = false) Integer endDate,
                                                  @ApiParam("Id of the needed wind farm")
                                                  @RequestParam(value = "winFarmId") Long winFarmId) {

        int startDateId = getDateId(startDate);
        int endDateId = getDateId(endDate);
        if (startDateId > endDateId) {
            throw new IllegalArgumentException(String.format("End date %d has to be later than start date %d or ignored.", endDate, startDate));
        }
        final List<ElectricityProductionAggregatedPerFarmAndDateVO> capacityFactorForRange = windFarmService.findCapacityFactorForRange(winFarmId, startDateId, endDateId);
        return capacityFactorForRange
                .stream()
                .map(ValuePerDateVO::new)
                .collect(Collectors.toList());

    }

    private int getDateId(final Integer date) {
        return ObjectUtils.defaultIfNull(date, DatesUtil.getTodaysDateId());
    }

}
