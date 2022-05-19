package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.RatingCateringMany;
import com.asaproject.asalife.domains.models.requests.RatingManyRequest;
import com.asaproject.asalife.domains.models.requests.RatingRequest;
import com.asaproject.asalife.domains.models.responses.RatingResponse;

import java.security.Principal;
import java.util.List;

public interface RatingCateringManyService {
    void addRatingCateringMany(Principal principal, RatingManyRequest ratingManyRequest) throws Exception;

    List<RatingCateringMany> getRatingCateringMany();
}
