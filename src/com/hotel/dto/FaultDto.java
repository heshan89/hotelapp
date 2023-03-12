package com.hotel.dto;

public class FaultDto {

    private Long id;
    private String description;
    private String severity;

    public FaultDto() {
    }

    public FaultDto(Long id, String description, String severity) {
        this.id = id;
        this.description = description;
        this.severity = severity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "FaultDto{" + "id=" + id + ", description=" + description + ", severity=" + severity + '}';
    }
}
