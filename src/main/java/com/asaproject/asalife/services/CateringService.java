package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.models.requests.AduanCatering;
import com.asaproject.asalife.domains.models.requests.StatusCatering;
import com.asaproject.asalife.domains.models.responses.CateringDto;

import java.security.Principal;
import java.util.List;

public interface CateringService {
    List<CateringDto> getCaterings();

    List<CateringDto> getCateringsByStatus(StatusCatering statusCatering);

    Catering getCateringById(Long id);

    List<CateringDto> addAduanCatering(Principal principal, AduanCatering aduanCatering) throws Exception;

    List<CateringDto> getUserCaterings(Principal principal);

    Catering updateStatusCatering(Long id, StatusCatering statusCatering) throws Exception;
}
