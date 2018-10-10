package com.pexapark.windfarm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class WindFarm {

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private Long capacity;

    @Column(nullable = false)
    private Long timezone;

    public WindFarm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(final Long capacity) {
        this.capacity = capacity;
    }

    public Long getTimezone() {
        return timezone;
    }

    public void setTimezone(final Long timezone) {
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
