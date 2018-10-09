package com.pexapark.windfarm.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class CapacityFactorVO {
    private String date;
    private BigDecimal capacityFactor;// TODO BigDecimal

    public CapacityFactorVO(String date, BigDecimal capacityFactor) {
        this.date = date;
        this.capacityFactor = capacityFactor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getCapacityFactor() {
        return capacityFactor;
    }

    public void setCapacityFactor(BigDecimal capacityFactor) {
        this.capacityFactor = capacityFactor;
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