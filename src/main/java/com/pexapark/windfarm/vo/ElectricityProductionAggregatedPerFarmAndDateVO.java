package com.pexapark.windfarm.vo;

import com.pexapark.windfarm.entity.WindFarm;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Current value object is used to retrieve result from SQL query with grouping.
 * Contains slots for farm, date and functionValue where functionValue might be either a sum, average or any relevant value
 */
public class ElectricityProductionAggregatedPerFarmAndDateVO {

    private WindFarm farm;
    private Integer date;
    private BigDecimal functionValue;

    public ElectricityProductionAggregatedPerFarmAndDateVO(final WindFarm farm, Integer date, BigDecimal functionValue) {
        this.farm = farm;
        this.date = date;
        this.functionValue = functionValue;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public BigDecimal getFunctionValue() {
        return functionValue;
    }

    public void setFunctionValue(BigDecimal functionValue) {
        this.functionValue = functionValue;
    }

    public WindFarm getFarm() {
        return farm;
    }

    public void setFarm(final WindFarm farm) {
        this.farm = farm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectricityProductionAggregatedPerFarmAndDateVO that = (ElectricityProductionAggregatedPerFarmAndDateVO) o;
        return Objects.equals(farm, that.farm) &&
                Objects.equals(date, that.date) &&
                Objects.equals(functionValue, that.functionValue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(farm, date, functionValue);
    }
}
