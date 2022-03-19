package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.RecordHousekeeping;
import com.asaproject.asalife.domains.models.requests.RecordHousekeepingRequest;
import com.asaproject.asalife.domains.models.responses.RecordHousekeepingDto;
import com.asaproject.asalife.domains.models.responses.RecordResponse;

import java.security.Principal;
import java.util.List;

public interface RecordHousekeepingService {
    List<RecordResponse> getAllRecord();

    List<RecordResponse> getMyRecord(Principal principal);

    List<RecordResponse> getAllByUser(String nrp) throws Exception;

    void addRecord(Principal principal, Long idRuangDetail, RecordHousekeepingRequest recordHousekeepingRequest) throws Exception;

    void verifyRecordStatus(Long idRecord, RecordHousekeepingRequest recordHousekeepingRequest) throws Exception;
}
