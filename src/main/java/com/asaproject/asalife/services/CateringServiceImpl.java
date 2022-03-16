package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.AduanCatering;
import com.asaproject.asalife.domains.models.requests.StatusCatering;
import com.asaproject.asalife.repositories.CateringRepository;
import com.asaproject.asalife.repositories.UserRepository;
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
    private final UserRepository userRepo;
    private final CateringRepository cateringRepo;

    @Override
    public List<Catering> getCaterings() {
        return cateringRepo.findAll();
    }

    @Override
    public List<Catering> getCateringsByStatus(StatusCatering statusCatering) {
        String status = StatusCateringUserMapper.mapStatus(statusCatering.getStatus());

        return cateringRepo.findAllByStatus(status);
    }

    @Override
    public Catering getCateringById(Long id)  {
        return cateringRepo.findCateringByIdNative(id);
    }

    @Override
    public List<Catering> addAduanCatering(Principal principal, AduanCatering aduanCatering) throws Exception {
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
    public List<Catering> getUserCaterings(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);

        List<Catering> caterings = cateringRepo.findByUser(user);
        return caterings;
    }

    @Override
    public Catering updateStatusCatering(Long id, StatusCatering statusCatering) throws Exception {
        Catering catering = cateringRepo.findCateringByIdNative(id);

        if (ObjectUtils.isEmpty(catering)) {
            throw new NotFoundException("NOT_FOUND");
        }
        String status = StatusCateringUserMapper.mapStatus(statusCatering.getStatus());

        catering.setStatus(status);
        cateringRepo.save(catering);
        return cateringRepo.findCateringByIdNative(id);
    }
}
