package com.pexapark.windfarm.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class CapacityFactorVO {

    private Long farmId;
    private Integer date;
    private BigDecimal capacityFactor;// TODO BigDecimal

    public CapacityFactorVO(final Long farmId, Integer date, BigDecimal capacityFactor) {
        this.farmId = farmId;
        this.date = date;
        this.capacityFactor = capacityFactor;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public BigDecimal getCapacityFactor() {
        return capacityFactor;
    }

    public void setCapacityFactor(BigDecimal capacityFactor) {
        this.capacityFactor = capacityFactor;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(final Long farmId) {
        this.farmId = farmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapacityFactorVO that = (CapacityFactorVO) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(capacityFactor.doubleValue(), that.capacityFactor.doubleValue());
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, capacityFactor);
    }
}
