package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.ECateringStatus;
import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.models.requests.AduanCatering;
import com.asaproject.asalife.domains.models.requests.StatusCatering;
import com.asaproject.asalife.domains.models.responses.CateringDto;

import java.security.Principal;
import java.util.List;

public interface CateringService {
    List<CateringDto> getCaterings();

    List<CateringDto> getCateringsByStatus(ECateringStatus eCateringStatus) throws Exception;

    CateringDto getCateringById(Long id) throws Exception;

    void addAduanCatering(Principal principal, AduanCatering aduanCatering) throws Exception;

    List<CateringDto> getUserCaterings(Principal principal);

    CateringDto updateStatusCatering(Long id, StatusCatering statusCatering) throws Exception;

    void deleteCatering(Long id) throws Exception;
}
