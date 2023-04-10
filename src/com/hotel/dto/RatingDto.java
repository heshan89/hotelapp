package com.hotel.dto;

import java.sql.Date;

public class RatingDto {

    private int id;
    private String ratingHotelName;
    private String ratingFeedbackCategoryName;
    private String ratingCategoryName;
    private String ratingSource;
    private int ratingValue;
    private String ratingDesc;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;

    public RatingDto() {
    }

    public RatingDto(int id, String ratingHotelName, String ratingFeedbackCategoryName, String ratingCategoryName, String ratingSource, int ratingValue, String ratingDesc, Date createdDate, String createdBy, Date updatedDate, String updatedBy) {
        this.id = id;
        this.ratingHotelName = ratingHotelName;
        this.ratingFeedbackCategoryName = ratingFeedbackCategoryName;
        this.ratingCategoryName = ratingCategoryName;
        this.ratingSource = ratingSource;
        this.ratingValue = ratingValue;
        this.ratingDesc = ratingDesc;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRatingHotelName() {
        return ratingHotelName;
    }

    public void setRatingHotelName(String ratingHotelName) {
        this.ratingHotelName = ratingHotelName;
    }

    public String getRatingFeedbackCategoryName() {
        return ratingFeedbackCategoryName;
    }

    public void setRatingFeedbackCategoryName(String ratingFeedbackCategoryName) {
        this.ratingFeedbackCategoryName = ratingFeedbackCategoryName;
    }

    public String getRatingCategoryName() {
        return ratingCategoryName;
    }

    public void setRatingCategoryName(String ratingCategoryName) {
        this.ratingCategoryName = ratingCategoryName;
    }

    public String getRatingSource() {
        return ratingSource;
    }

    public void setRatingSource(String ratingSource) {
        this.ratingSource = ratingSource;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getRatingDesc() {
        return ratingDesc;
    }

    public void setRatingDesc(String ratingDesc) {
        this.ratingDesc = ratingDesc;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "RatingDto{" +
                "id=" + id +
                ", ratingHotelName='" + ratingHotelName + '\'' +
                ", ratingFeedbackCategoryName='" + ratingFeedbackCategoryName + '\'' +
                ", ratingCategoryName='" + ratingCategoryName + '\'' +
                ", ratingSource='" + ratingSource + '\'' +
                ", ratingValue=" + ratingValue +
                ", ratingDesc='" + ratingDesc + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
