/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.model;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author betzler
 */
public class EntityView {
//@NotNull on other fields?
    private int entityId;
    @NotEmpty(message = "A Superhero/Supervillain Name is required.")
    @Length(max = 50, message = "A Superhero/Supervillain Name may not exceed 50 characters.")
    private String entityName;
    @NotEmpty(message = "A Superhero/Supervillain Description is required.")
    @Length(max = 255, message = "A Superhero/Supervillain Description may not exceed 255 characters.")
    private String entityDescription;
    @NotEmpty(message = "At least one Super Power is required.")
    private List<Integer> powerIds;
    @NotNull(message = "Indicating Hero or Villain is required.")
    private boolean isHero;
    @NotEmpty(message = "At least one Organization is required.")
    private List<Integer> organizationIds;

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

    public List<Integer> getPowerIds() {
        return powerIds;
    }

    public void setPowerIds(List<Integer> powerIds) {
        this.powerIds = powerIds;
    }

    public boolean isIsHero() {
        return isHero;
    }

    public void setIsHero(boolean isHero) {
        this.isHero = isHero;
    }

    public List<Integer> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(List<Integer> organizationIds) {
        this.organizationIds = organizationIds;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.entityId;
        hash = 61 * hash + Objects.hashCode(this.entityName);
        hash = 61 * hash + Objects.hashCode(this.entityDescription);
        hash = 61 * hash + Objects.hashCode(this.powerIds);
        hash = 61 * hash + (this.isHero ? 1 : 0);
        hash = 61 * hash + Objects.hashCode(this.organizationIds);
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
        final EntityView other = (EntityView) obj;
        if (this.entityId != other.entityId) {
            return false;
        }
        if (this.isHero != other.isHero) {
            return false;
        }
        if (!Objects.equals(this.entityName, other.entityName)) {
            return false;
        }
        if (!Objects.equals(this.entityDescription, other.entityDescription)) {
            return false;
        }
        if (!Objects.equals(this.powerIds, other.powerIds)) {
            return false;
        }
        if (!Objects.equals(this.organizationIds, other.organizationIds)) {
            return false;
        }
        return true;
    }

}
