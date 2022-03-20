package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Bobot;
import com.asaproject.asalife.domains.entities.RatingCatering;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.RatingRequest;
import com.asaproject.asalife.domains.models.responses.RatingCateringDto;
import com.asaproject.asalife.domains.models.responses.RatingResponse;

import java.security.Principal;
import java.util.List;

public interface RatingCateringService {
    void addRatingCatering(Principal principal, RatingRequest ratingRequest) throws Exception;

    void addRatingCateringBulk(Principal principal, List<RatingRequest> ratingRequestList) throws Exception;

    List<RatingResponse> getUSerRatingCatering(Principal principal);

    List<RatingResponse> getAllRatingCatering();

    Boolean isAddRatingCateringAvailable(Principal principal);
}
