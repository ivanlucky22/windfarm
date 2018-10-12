package com.pexapark.windfarm.vo;

import com.pexapark.windfarm.entity.WindFarm;

import java.math.BigDecimal;
import java.util.Objects;

public class ElectricityProductionAggregatedPerFarmAndDateVO {

    private WindFarm farmId;
    private Integer date;
    private BigDecimal value;

    public ElectricityProductionAggregatedPerFarmAndDateVO(final WindFarm farmId, Integer date, BigDecimal value) {
        this.farmId = farmId;
        this.date = date;
        this.value = value;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public WindFarm getFarmId() {
        return farmId;
    }

    public void setFarmId(final WindFarm farmId) {
        this.farmId = farmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectricityProductionAggregatedPerFarmAndDateVO that = (ElectricityProductionAggregatedPerFarmAndDateVO) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(value.doubleValue(), that.value.doubleValue());
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, value);
    }
}
