package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Bobot;
import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.models.requests.BobotRequest;
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
public class BobotServiceImpl implements BobotService {
    private final BobotRepository bobotRepository;
    private final PertanyaanRepository pertanyaanRepository;

    @Override
    public List<Bobot> getListBobot() {
        return bobotRepository.findAll();
    }

    @Override
    public List<Bobot> addBobot(BobotRequest bobotRequest) throws Exception {
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(bobotRequest.getId_pertanyaan());

        if (ObjectUtils.isEmpty(pertanyaan) || !ObjectUtils.isEmpty(pertanyaan.getDeletedAt())) {
            throw new NotFoundException("PERTANYAAN NOT FOUND");
        }

        Bobot bobot = new Bobot();
        bobot.setPilihan(bobotRequest.getPilihan());
        bobot.setNilai(bobotRequest.getNilai());
        bobot.setPertanyaan(pertanyaan);
        bobotRepository.save(bobot);

        return bobotRepository.findAllByPertanyaan_Id(bobotRequest.getId_pertanyaan());
    }

    @Override
    public List<Bobot> getListBobotByPertanyaan(Long id) throws Exception {
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(id);
        if (ObjectUtils.isEmpty(pertanyaan)  || !ObjectUtils.isEmpty(pertanyaan.getDeletedAt())) {
            throw new NotFoundException("PERTANYAAN NOT FOUND");
        }
        return bobotRepository.findAllByPertanyaan_Id(id);
    }

    @Override
    public List<Bobot> deleteBobot(Long id) throws Exception {
        Bobot bobot = bobotRepository.findBobotByIdNative(id);
        if (ObjectUtils.isEmpty(bobot) || !ObjectUtils.isEmpty(bobot.getDeletedAt())) {
            throw new NotFoundException("BOBOT NOT FOUND");
        }

        bobot.setDeletedAt(new Date());
        return bobotRepository.findAllByPertanyaan(bobot.getPertanyaan());
    }
}
