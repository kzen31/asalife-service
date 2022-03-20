package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Mess;
import com.asaproject.asalife.domains.models.requests.MessRequest;

import java.util.List;

public interface MessService {
    List<Mess> getAllMess();

    List<Mess> addMess(MessRequest messRequest) throws Exception;

    Boolean isMessAvailable(String name);

    void deleteMess(MessRequest messRequest) throws Exception;
}
