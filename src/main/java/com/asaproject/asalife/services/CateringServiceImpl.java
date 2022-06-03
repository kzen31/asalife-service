package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.ECateringStatus;
import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.AduanCatering;
import com.asaproject.asalife.domains.models.requests.StatusCatering;
import com.asaproject.asalife.domains.models.responses.CateringDto;
import com.asaproject.asalife.repositories.CateringRepository;
import com.asaproject.asalife.repositories.UserRepository;
import com.asaproject.asalife.utils.mappers.CateringMapper;
import com.asaproject.asalife.utils.mappers.StatusCateringUserMapper;
import com.asaproject.asalife.utils.mappers.UserAdminMapper;
//import com.sun.media.sound.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CateringServiceImpl implements CateringService{
    private final CateringRepository cateringRepo;
    private final CateringMapper cateringMapper;

    @Override
    public List<CateringDto> getCaterings() {
        return cateringMapper.mapCateringDtoToList(cateringRepo.findAllByOrderByCreatedAtAsc());
    }

    @Override
    public List<CateringDto> getCateringsByStatus(ECateringStatus eCateringStatus) throws Exception {

        if (ObjectUtils.isEmpty(eCateringStatus)) {
            return getCaterings();
        }

        String status = StatusCateringUserMapper.mapStatus(eCateringStatus);
        return cateringMapper.mapCateringDtoToList(cateringRepo.findAllByStatusOrderByCreatedAtAsc(status));
    }

    @Override
    public CateringDto getCateringById(Long id) throws Exception {
        Catering catering = cateringRepo.findCateringByIdNative(id);
        if (ObjectUtils.isEmpty(catering)) {
            throw new NotFoundException("Catering Not Found");
        }
        CateringDto cateringDto = cateringMapper.entityToCateringDto(cateringRepo.findCateringByIdNative(id));
        return cateringDto;
    }

    @Override
    public void addAduanCatering(Principal principal, AduanCatering aduanCatering) throws Exception {
        User user = UserAdminMapper.principalToUser(principal);
        Catering catering = new Catering();

        catering.setUser(user);
        catering.setLokasi(aduanCatering.getLokasi());
        catering.setKritik_saran(aduanCatering.getKritik_saran());
        catering.setDeskripsi(aduanCatering.getDeskripsi());
        cateringRepo.save(catering);
    }

    @Override
    public List<CateringDto> getUserCaterings(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);

        List<Catering> caterings = cateringRepo.findByUserOrderByCreatedAtAsc(user);
        return cateringMapper.mapCateringDtoToList(caterings);
    }

    @Override
    public CateringDto updateStatusCatering(Long id, StatusCatering statusCatering) throws Exception {
        Catering catering = cateringRepo.findCateringByIdNative(id);

        if (ObjectUtils.isEmpty(catering)) {
            throw new NotFoundException("NOT_FOUND");
        }
        String status = StatusCateringUserMapper.mapStatus(statusCatering.getStatus());

        catering.setStatus(status);
        cateringRepo.save(catering);
        return cateringMapper.entityToCateringDto(cateringRepo.findCateringByIdNative(id));
    }

    @Override
    public void deleteCatering(Long id) throws Exception {
        Catering catering = cateringRepo.findCateringByIdNative(id);

        if(ObjectUtils.isEmpty(catering)) {
            throw new NotFoundException("NOT_FOUND");
        }

        if (!ObjectUtils.isEmpty(catering.getDeletedAt())) {
            throw new NotFoundException("NOT_FOUND");
        }
        catering.setDeletedAt(new Date());
        cateringRepo.save(catering);
    }
}
