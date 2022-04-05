package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.models.requests.PertanyaanRequest;
import com.asaproject.asalife.domains.models.responses.PertanyaanDto;

import java.util.List;

public interface PertanyaanService {
    List<PertanyaanDto> getAllPertanyaan();

    void addPertanyaan(PertanyaanRequest pertanyaanRequest);

    void deletePertanyaan(Long id) throws Exception;

    void deletePertanyaanBobot(Long id);

    PertanyaanDto updatePertanyaanIfExist(Long id, PertanyaanRequest pertanyaanRequest) throws Exception;

    Pertanyaan updatePertanyaan(Long id, PertanyaanRequest pertanyaanRequest);

    PertanyaanDto getPertanyaanByID(Long id) throws Exception;
}
