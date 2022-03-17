package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Bobot;
import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.models.requests.PertanyaanRequest;
import com.asaproject.asalife.repositories.BobotRepository;
import com.asaproject.asalife.repositories.PertanyaanRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PertanyaanServiceImpl implements PertanyaanService{
    private final PertanyaanRepository pertanyaanRepository;
    private final BobotRepository bobotRepository;

    @Override
    public List<Pertanyaan> getAllPertanyaan() {
        return pertanyaanRepository.findAll();
    }

    @Override
    public Pertanyaan addPertanyaan(PertanyaanRequest pertanyaanRequest) {
        Pertanyaan pertanyaan = new Pertanyaan();
        pertanyaan.setIsi(pertanyaanRequest.getIsi());
        return pertanyaanRepository.save(pertanyaan);
    }

    @Override
    public void deletePertanyaan(Long id) throws Exception{
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(id);
        if (ObjectUtils.isEmpty(pertanyaan)) {
            throw new NotFoundException("PERTANYAAN NOT FOUND");
        }

        pertanyaan.setDeletedAt(new Date());
        pertanyaanRepository.save(pertanyaan);
        deletePertanyaanBobot(pertanyaan);
    }

    @Override
    public void deletePertanyaanBobot(Pertanyaan pertanyaan) {
        List<Bobot> bobotList = bobotRepository.findAllByPertanyaan(pertanyaan);

        for (Bobot bobot : bobotList) {
            bobot.setDeletedAt(new Date());
            bobotRepository.save(bobot);
        }
    }

    @Override
    public Pertanyaan updatePertanyaanIfExist(Long id, PertanyaanRequest pertanyaanRequest) throws Exception {
        Pertanyaan pertanyaan = getPertanyaanByID(id);
        if (ObjectUtils.isEmpty(pertanyaan)) {
            throw new NotFoundException("PERTANYAAN NOT FOUND");
        } else
            return updatePertanyaan(id, pertanyaanRequest);
    }

    @Override
    public Pertanyaan updatePertanyaan(Long id, PertanyaanRequest pertanyaanRequest) {
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(id);

        pertanyaan.setIsi(pertanyaanRequest.getIsi());
        return pertanyaanRepository.save(pertanyaan);
    }

    @Override
    public Pertanyaan getPertanyaanByID(Long id) throws Exception {
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(id);
        if (ObjectUtils.isEmpty(pertanyaan)) {
            throw new NotFoundException("PERTANYAAN NOT FOUND");
        }
        return pertanyaan;
    }
}
