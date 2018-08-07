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
public class Sighting {

    private int sightingId;
    @NotNull(message = "A Location is required.")
    private Location location;
    @NotEmpty(message = "At least one Superhero or Supervillain is required.")
    private List<Entity> entities;
    //The pattern is the format you want it to go out in JSON as.  It should work the same for LocalDateTime.
    @JsonFormat(pattern = "MM-dd-yyyy H:mm:ss")
    private LocalDateTime sightingDateTime;

    public Sighting() {
    }

    public Sighting(SightingView sightingView, List<Entity> entities, Location location) {
        this.location = location;
        this.entities = entities;
        this.sightingDateTime = sightingView.getSightingDateTime();
    }

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
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
        hash = 67 * hash + this.sightingId;
        hash = 67 * hash + Objects.hashCode(this.location);
        hash = 67 * hash + Objects.hashCode(this.entities);
        hash = 67 * hash + Objects.hashCode(this.sightingDateTime);
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
        final Sighting other = (Sighting) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.entities, other.entities)) {
            return false;
        }
        if (!Objects.equals(this.sightingDateTime, other.sightingDateTime)) {
            return false;
        }
        return true;
    }
}
