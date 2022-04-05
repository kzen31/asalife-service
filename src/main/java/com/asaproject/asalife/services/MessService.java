package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.models.requests.MessRequest;
import com.asaproject.asalife.domains.models.responses.MessDto;

import java.util.List;

public interface MessService {
    List<MessDto> getAllMess();

    void addMess(MessRequest messRequest) throws Exception;

    Boolean isMessAvailable(String name);

    void deleteMess(Long id) throws Exception;
}
