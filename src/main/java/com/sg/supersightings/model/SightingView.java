/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author betzler
 */
public class SightingView {

    private int sightingId;
    @NotNull(message = "A Location is required.")
    private int locationId;
    @NotEmpty(message = "At least one Superhero or Supervillain is required.")
    private List<Integer> entityIds;
    //The pattern is the format you want it to go out in JSON as.  It should work the same for LocalDateTime.
//    @JsonFormat(pattern = "yyyy-MM-ddTH:mm:ss")
//    @NotEmpty(message = "A Date and Time is required.")
    private LocalDateTime sightingDateTime;

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public List<Integer> getEntityIds() {
        return entityIds;
    }

    public void setEntityIds(List<Integer> entityIds) {
        this.entityIds = entityIds;
    }

    public LocalDateTime getSightingDateTime() {
        return sightingDateTime;
    }

    public void setSightingDateTime(LocalDateTime sightingDateTime) {
        this.sightingDateTime = sightingDateTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.sightingId;
        hash = 23 * hash + this.locationId;
        hash = 23 * hash + Objects.hashCode(this.entityIds);
        hash = 23 * hash + Objects.hashCode(this.sightingDateTime);
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
        final SightingView other = (SightingView) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (this.locationId != other.locationId) {
            return false;
        }
        if (!Objects.equals(this.entityIds, other.entityIds)) {
            return false;
        }
        if (!Objects.equals(this.sightingDateTime, other.sightingDateTime)) {
            return false;
        }
        return true;
    }

}
