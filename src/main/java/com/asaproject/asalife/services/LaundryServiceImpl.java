package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Laundry;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.LaundryRequest;
import com.asaproject.asalife.domains.models.requests.StatusLaundry;
import com.asaproject.asalife.domains.models.responses.LaundryDto;
import com.asaproject.asalife.repositories.LaundryRepository;
import com.asaproject.asalife.utils.mappers.LaundryMapper;
import com.asaproject.asalife.utils.mappers.StatusLaundryUserMapper;
import com.asaproject.asalife.utils.mappers.UserAdminMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LaundryServiceImpl implements LaundryService{
    private final LaundryRepository laundryRepository;
    private final LaundryMapper laundryMapper;

    @Override
    public List<LaundryDto> getAllLaundryDto() {
        return laundryMapper.createListLaundryDto(getAllLaundry());
    }

    @Override
    public List<LaundryDto> getAllLaundryByUserDto(Principal principal) {
        return laundryMapper.createListLaundryDto(getAllLaundryByUser(principal));
    }

    @Override
    public List<Laundry> getAllLaundry() {
       return laundryRepository.findAll();
    }


    @Override
    public List<Laundry> getAllLaundryByUser(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);
        return laundryRepository.findAllByUser(user);
    }

    @Override
    public void addLaundry(Principal principal, LaundryRequest laundryRequest) {
        User user = UserAdminMapper.principalToUser(principal);
        Laundry laundry = new Laundry();

        laundry.setNo_kamar(laundryRequest.getNo_kamar());
        laundry.setJenis_deviasi(laundryRequest.getJenis_deviasi());
        laundry.setTanggal_laundry(laundryRequest.getTanggal_loundry());
        laundry.setJenis_pakaian(laundryRequest.getJenis_pakaian());
        laundry.setMess(laundryRequest.getMess());
        laundry.setUser(user);
        laundryRepository.save(laundry);
    }

    @Override
    public List<LaundryDto> updateStatusLaundry(Long id, @RequestBody StatusLaundry statusLaundry) throws Exception{
        if (!isLaundryExist(id)) {
            throw new NotFoundException("LAUNDRY_NOT_FOUND");
        }
        String status = StatusLaundryUserMapper.mapStatus(statusLaundry.getStatus());

        Laundry laundry = laundryRepository.findLaundryByIdNative(id);
        laundry.setStatus(status);
        laundryRepository.save(laundry);

        return getAllLaundryDto();
    }

    @Override
    public Boolean isLaundryExist(Long id){
        return !ObjectUtils.isEmpty(laundryRepository.findById(id));
    }
}