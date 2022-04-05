package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Bobot;
import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.models.requests.PertanyaanRequest;
import com.asaproject.asalife.domains.models.responses.PertanyaanDto;
import com.asaproject.asalife.repositories.BobotRepository;
import com.asaproject.asalife.repositories.PertanyaanRepository;
import com.asaproject.asalife.utils.mappers.PertanyaanMapper;
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
    private final PertanyaanMapper pertanyaanMapper;

    @Override
    public List<PertanyaanDto> getAllPertanyaan() {
        return pertanyaanMapper.mapPertanyaanDtoToList(pertanyaanRepository.findAll());
    }

    @Override
    public void addPertanyaan(PertanyaanRequest pertanyaanRequest) {
        Pertanyaan pertanyaan = new Pertanyaan();
        pertanyaan.setIsi(pertanyaanRequest.getIsi());
        pertanyaanRepository.save(pertanyaan);
    }

    @Override
    public void deletePertanyaan(Long id) throws Exception{
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(id);
        if (ObjectUtils.isEmpty(pertanyaan) || !ObjectUtils.isEmpty(pertanyaan.getDeletedAt())) {
            throw new NotFoundException("PERTANYAAN NOT FOUND");
        }

        deletePertanyaanBobot(id);
        pertanyaan.setDeletedAt(new Date());
    }

    @Override
    public void deletePertanyaanBobot(Long id) {
        List<Bobot> bobotList = bobotRepository.findAllByPertanyaan_Id(id);

        if (!ObjectUtils.isEmpty(bobotList)) {
            for (Bobot bobot : bobotList) {
                bobot.setDeletedAt(new Date());
            }
        }
    }

    @Override
    public PertanyaanDto updatePertanyaanIfExist(Long id, PertanyaanRequest pertanyaanRequest) throws Exception {
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(id);
        if (ObjectUtils.isEmpty(pertanyaan) || !ObjectUtils.isEmpty(pertanyaan.getDeletedAt())) {
            throw new NotFoundException("PERTANYAAN NOT FOUND");
        } else
            return pertanyaanMapper.entityToPertanyaanDto(updatePertanyaan(id, pertanyaanRequest));
    }

    @Override
    public Pertanyaan updatePertanyaan(Long id, PertanyaanRequest pertanyaanRequest) {
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(id);

        pertanyaan.setIsi(pertanyaanRequest.getIsi());
        return pertanyaanRepository.save(pertanyaan);
    }

    @Override
    public PertanyaanDto getPertanyaanByID(Long id) throws Exception {
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(id);
        if (ObjectUtils.isEmpty(pertanyaan) || !ObjectUtils.isEmpty(pertanyaan.getDeletedAt())) {
            throw new NotFoundException("PERTANYAAN NOT FOUND");
        }
        return pertanyaanMapper.entityToPertanyaanDto(pertanyaan);
    }
}
