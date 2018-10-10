package com.pexapark.windfarm.entity;

import javax.persistence.Entity;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
public class ElectricityProduction {

    private WindFarm windFarm;
    private ZonedDateTime timestamp;
    private Long electricityProduced;

    public ElectricityProduction(final WindFarm windFarm, final ZonedDateTime timestamp, final Long electricityProduced) {
        this.windFarm = windFarm;
        this.timestamp = timestamp;
        this.electricityProduced = electricityProduced;
    }

    public WindFarm getWindFarm() {
        return windFarm;
    }

    public void setWindFarm(final WindFarm windFarm) {
        this.windFarm = windFarm;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getElectricityProduced() {
        return electricityProduced;
    }

    public void setElectricityProduced(final Long electricityProduced) {
        this.electricityProduced = electricityProduced;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ElectricityProduction that = (ElectricityProduction) o;
        return Objects.equals(windFarm, that.windFarm) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(electricityProduced, that.electricityProduced);
    }

    @Override
    public int hashCode() {

        return Objects.hash(windFarm, timestamp, electricityProduced);
    }
}
