package org.hw17.model.enums;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum LoanType {
    EDUCATIONAL(Availability.PER_COURSE),
    HOUSE_RENT(Availability.PER_GRADE),
    TUITION(Availability.PER_GRADE);

    @Enumerated(value = EnumType.STRING)
    private final Availability availability;

    LoanType(Availability availability) {
        this.availability = availability;
    }

    public Availability getAvailability() {
        return this.availability;
    }
}
