/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author betzler
 */
public class Location {

    private int locationId;
    @NotEmpty(message = "A Location Name is required.")
    @Length(max = 50, message = "A Location Name may not exceed 50 characters.")
    private String locationName;
    @NotEmpty(message = "A Location Description is required.")
    @Length(max = 255, message = "A Location Description may not exceed 255 characters.")
    private String locationDescription;
    @Length(max = 75, message = "A Location Address may not exceed 75 characters.")
    private String locationAddress;
    @Digits(integer = 2, fraction = 6, message = "A Location Latitude is limited to a precision of six decimal places.")
    @Range(min = -90, max = 90, message = "The value range for Latitude is -90 to 90.")
    private BigDecimal locationLatitude;
    @Digits(integer = 3, fraction = 6, message = "A Location Longitude is limited to a precision of six decimal places.")
    @Range(min = -180, max = 180, message = "The value range for Longitude is -180 to 180.")
    private BigDecimal locationLongitude;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public BigDecimal getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(BigDecimal locationLatitude) {
        this.locationLatitude = locationLatitude.setScale(6);
    }

    public BigDecimal getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(BigDecimal locationLongitude) {
        this.locationLongitude = locationLongitude.setScale(6);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.locationId;
        hash = 41 * hash + Objects.hashCode(this.locationName);
        hash = 41 * hash + Objects.hashCode(this.locationDescription);
        hash = 41 * hash + Objects.hashCode(this.locationAddress);
        hash = 41 * hash + Objects.hashCode(this.locationLatitude);
        hash = 41 * hash + Objects.hashCode(this.locationLongitude);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.locationId != other.locationId) {
            return false;
        }
        if (!Objects.equals(this.locationName, other.locationName)) {
            return false;
        }
        if (!Objects.equals(this.locationDescription, other.locationDescription)) {
            return false;
        }
        if (!Objects.equals(this.locationAddress, other.locationAddress)) {
            return false;
        }
        if (!Objects.equals(this.locationLatitude, other.locationLatitude)) {
            return false;
        }
        if (!Objects.equals(this.locationLongitude, other.locationLongitude)) {
            return false;
        }
        return true;
    }
}
