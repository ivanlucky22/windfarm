package com.pexapark.windfarm.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "electricity_production", schema = "public")
public class ElectricityProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private WindFarm windFarm;
    private Integer dateId;
    private Integer timeId;
    private BigDecimal electricityProduced;

    public ElectricityProduction() {
    }

    public ElectricityProduction(final WindFarm windFarm, final Integer dateId, final Integer timeId, final BigDecimal electricityProduced) {
        this.windFarm = windFarm;
        this.dateId = dateId;
        this.timeId = timeId;
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

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(final Integer dateId) {
        this.dateId = dateId;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(final Integer timeId) {
        this.timeId = timeId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ElectricityProduction that = (ElectricityProduction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(windFarm, that.windFarm) &&
                Objects.equals(dateId, that.dateId) &&
                Objects.equals(timeId, that.timeId) &&
                Objects.equals(electricityProduced, that.electricityProduced);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, windFarm, dateId, timeId, electricityProduced);
    }
}
