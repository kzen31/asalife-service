package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Bobot;
import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.models.requests.BobotRequest;
import com.asaproject.asalife.domains.models.responses.BobotDto;

import java.util.List;

public interface BobotService {
    List<BobotDto> getListBobot ();

    void addBobot(BobotRequest bobotRequest) throws Exception;

    List<BobotDto> getListBobotByPertanyaan (Long id) throws Exception;

    void deleteBobot (Long id) throws Exception;
}
