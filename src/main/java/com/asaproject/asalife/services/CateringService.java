package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.models.requests.AduanCatering;
import com.asaproject.asalife.domains.models.requests.StatusCatering;

import java.security.Principal;
import java.util.List;

public interface CateringService {
    List<Catering> getCaterings();

    List<Catering> getCateringsByStatus(StatusCatering statusCatering);

    Catering getCateringById(Long id);

    List<Catering> addAduanCatering(Principal principal, AduanCatering aduanCatering) throws Exception;

    List<Catering> getUserCaterings(Principal principal);

    Catering updateStatusCatering(Long id, StatusCatering statusCatering) throws Exception;
}
