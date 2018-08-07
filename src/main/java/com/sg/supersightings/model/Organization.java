/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.model;

import java.util.Objects;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author betzler
 */
public class Organization {

    private int organizationId;
    @NotEmpty(message = "An Organization Name is required.")
    @Length(max = 50, message = "An Organization Name may not exceed 50 characters.")
    private String organizationName;
    @NotEmpty(message = "An Organization Description is required.")
    @Length(max = 255, message = "An Organization Description may not exceed 50 characters.")
    private String organizationDescription;
    @NotEmpty(message = "An Organization Addresss is required.")
    @Length(max = 75, message = "An Organization Address may not exceed 50 characters.")
    private String organizationAddress;
    @Email(message = "Please enter a valid email address.")
    @Length(max = 50, message = "An email address may not exceed 50 characters.")
    private String organizationEmail;
    @Length(max = 15, message = "A phone number may not exceed 15 characters.")
    private String organizationPhone;

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationDescription() {
        return organizationDescription;
    }

    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public String getOrganizationEmail() {
        return organizationEmail;
    }

    public void setOrganizationEmail(String organizationEmail) {
        this.organizationEmail = organizationEmail;
    }

    public String getOrganizationPhone() {
        return organizationPhone;
    }

    public void setOrganizationPhone(String organizationPhone) {
        this.organizationPhone = organizationPhone;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.organizationId;
        hash = 89 * hash + Objects.hashCode(this.organizationName);
        hash = 89 * hash + Objects.hashCode(this.organizationDescription);
        hash = 89 * hash + Objects.hashCode(this.organizationAddress);
        hash = 89 * hash + Objects.hashCode(this.organizationEmail);
        hash = 89 * hash + Objects.hashCode(this.organizationPhone);
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
        final Organization other = (Organization) obj;
        if (this.organizationId != other.organizationId) {
            return false;
        }
        if (!Objects.equals(this.organizationName, other.organizationName)) {
            return false;
        }
        if (!Objects.equals(this.organizationDescription, other.organizationDescription)) {
            return false;
        }
        if (!Objects.equals(this.organizationAddress, other.organizationAddress)) {
            return false;
        }
        if (!Objects.equals(this.organizationEmail, other.organizationEmail)) {
            return false;
        }
        if (!Objects.equals(this.organizationPhone, other.organizationPhone)) {
            return false;
        }
        return true;
    }

}
