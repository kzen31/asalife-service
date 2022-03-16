package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Bobot;
import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.models.requests.BobotRequest;

import java.util.List;

public interface BobotService {
    List<Bobot> getListBobot ();

    List<Bobot> addBobot(BobotRequest bobotRequest) throws Exception;

    List<Bobot> getListBobotByPertanyaan (Long id) throws Exception;

    List<Bobot> deleteBobot (Long id) throws Exception;
}
