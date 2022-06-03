package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.models.requests.HousekeepingRequest;
import com.asaproject.asalife.domains.models.requests.StatusHousekeeping;
import com.asaproject.asalife.domains.models.responses.HousekeepingDto;

import java.security.Principal;
import java.util.List;

public interface HousekeepingService {
    List<HousekeepingDto> getAll();

    List<HousekeepingDto> getAllByUser(Principal principal);

    void addByUser(Principal principal, HousekeepingRequest housekeepingRequest);

    void updateStatusHousekeeping(Long id, StatusHousekeeping statusHousekeeping) throws Exception;

    Boolean isHousekeepingExist(Long id);

    void deleteHousekeeping(Long id) throws Exception;
}
