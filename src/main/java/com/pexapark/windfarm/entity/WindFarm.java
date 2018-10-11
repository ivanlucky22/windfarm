package com.pexapark.windfarm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
public class WindFarm {

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private BigDecimal capacity;

    @Column(nullable = false)
    private String timezone;

    public WindFarm() {
    }

    public WindFarm(BigDecimal capacity, String timezone) {
        this.capacity = capacity;
        this.timezone = timezone;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(final BigDecimal capacity) {
        this.capacity = capacity;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(final String timezone) {
        this.timezone = timezone;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final WindFarm windFarm = (WindFarm) o;
        return Objects.equals(id, windFarm.id) &&
                Objects.equals(capacity, windFarm.capacity) &&
                Objects.equals(timezone, windFarm.timezone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, capacity, timezone);
    }
}
