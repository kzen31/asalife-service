package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Bobot;
import com.asaproject.asalife.domains.entities.RatingCatering;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.RatingRequest;
import com.asaproject.asalife.domains.models.responses.RatingCateringDto;

import java.security.Principal;
import java.util.List;

public interface RatingCateringService {
    Boolean addRatingCatering(Principal principal, RatingRequest ratingRequest) throws Exception;

    void addRatingCateringBulk(Principal principal, List<RatingRequest> ratingRequestList) throws Exception;

    List<RatingCateringDto> getAllRatingCatering();

    Boolean isAddRatingCateringAvailable(Principal principal);
}
