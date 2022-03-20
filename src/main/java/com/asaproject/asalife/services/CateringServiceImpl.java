package com.asaproject.asalife.services;

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
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CateringServiceImpl implements CateringService{
    private final CateringRepository cateringRepo;
    private final CateringMapper cateringMapper;

    @Override
    public List<CateringDto> getCaterings() {
        return cateringMapper.mapCateringDtoToList(cateringRepo.findAll());
    }

    @Override
    public List<CateringDto> getCateringsByStatus(StatusCatering statusCatering) {
        String status = StatusCateringUserMapper.mapStatus(statusCatering.getStatus());

        return cateringMapper.mapCateringDtoToList(cateringRepo.findAllByStatus(status));
    }

    @Override
    public CateringDto getCateringById(Long id) throws Exception {
        CateringDto cateringDto = cateringMapper.entityToCateringDto(cateringRepo.findCateringByIdNative(id));
        if (ObjectUtils.isEmpty(cateringDto)) {
            throw new NotFoundException("CATERING_WITH_ID_NOT_FOUND");
        }
        return cateringDto;
    }

    @Override
    public List<CateringDto> addAduanCatering(Principal principal, AduanCatering aduanCatering) throws Exception {
        User user = UserAdminMapper.principalToUser(principal);
        Catering catering = new Catering();

        catering.setUser(user);
        catering.setLokasi(aduanCatering.getLokasi());
        catering.setKritik_saran(aduanCatering.getKritik_saran());
        catering.setDeskripsi(aduanCatering.getDeskripsi());
        cateringRepo.save(catering);

        return getUserCaterings(principal);
    }

    @Override
    public List<CateringDto> getUserCaterings(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);

        List<Catering> caterings = cateringRepo.findByUser(user);
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
}
