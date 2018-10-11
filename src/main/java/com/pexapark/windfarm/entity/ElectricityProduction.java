package com.pexapark.windfarm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class ElectricityProduction {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private WindFarm windFarm;
    private Long date;
    private Long time;
    private BigDecimal electricityProduced;

    public ElectricityProduction() {
    }

    public ElectricityProduction(final WindFarm windFarm, final Long date, final Long time, final BigDecimal electricityProduced) {
        this.windFarm = windFarm;
        this.date = date;
        this.time = time;
        this.electricityProduced = electricityProduced;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WindFarm getWindFarm() {
        return windFarm;
    }

    public void setWindFarm(final WindFarm windFarm) {
        this.windFarm = windFarm;
    }

    public BigDecimal getElectricityProduced() {
        return electricityProduced;
    }

    public void setElectricityProduced(final BigDecimal electricityProduced) {
        this.electricityProduced = electricityProduced;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(final Long date) {
        this.date = date;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(final Long time) {
        this.time = time;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ElectricityProduction that = (ElectricityProduction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(windFarm, that.windFarm) &&
                Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(electricityProduced, that.electricityProduced);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, windFarm, date, time, electricityProduced);
    }
}
