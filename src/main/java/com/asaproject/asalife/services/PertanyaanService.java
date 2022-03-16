package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.models.requests.PertanyaanRequest;

import java.util.List;

public interface PertanyaanService {
    List<Pertanyaan> getAllPertanyaan();

    Pertanyaan addPertanyaan(PertanyaanRequest pertanyaanRequest);

    void deletePertanyaan(Long id) throws Exception;

    Pertanyaan updatePertanyaanIfExist(Long id, PertanyaanRequest pertanyaanRequest) throws Exception;

    Pertanyaan updatePertanyaan(Long id, PertanyaanRequest pertanyaanRequest);

    Pertanyaan getPertanyaanByID(Long id) throws Exception;
}
