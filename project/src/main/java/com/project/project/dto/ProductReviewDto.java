package com.project.project.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class ProductReviewDto {

    private String review;

    @NotNull
    @Range(min = 0, max = 10)
    private String rating;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
