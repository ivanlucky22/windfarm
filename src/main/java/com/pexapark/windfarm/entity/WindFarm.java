package com.pexapark.windfarm.entity;

import com.pexapark.windfarm.common.Constants;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
public class WindFarm {

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private BigDecimal hourlyCapacity;

    @Column(nullable = false)
    private String timezone;

    @Transient
    private BigDecimal dailyCapacity;

    public WindFarm() {
    }

    public WindFarm(BigDecimal hourlyCapacity, String timezone) {
        this.hourlyCapacity = hourlyCapacity;
        this.timezone = timezone;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getHourlyCapacity() {
        return hourlyCapacity;
    }

    public void setHourlyCapacity(final BigDecimal hourlyCapacity) {
        this.hourlyCapacity = hourlyCapacity;
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
                Objects.equals(hourlyCapacity, windFarm.hourlyCapacity) &&
                Objects.equals(timezone, windFarm.timezone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, hourlyCapacity, timezone);
    }

    public BigDecimal getDailyCapacity() {
        if (dailyCapacity == null) {
            dailyCapacity = getHourlyCapacity().multiply(new BigDecimal(Constants.HOURS_IN_DAY));
        }
        return dailyCapacity;
    }

}
