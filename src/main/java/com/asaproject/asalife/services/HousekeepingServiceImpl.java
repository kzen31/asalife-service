package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Housekeeping;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.HousekeepingRequest;
import com.asaproject.asalife.domains.models.requests.StatusHousekeeping;
import com.asaproject.asalife.domains.models.responses.HousekeepingDto;
import com.asaproject.asalife.repositories.HousekeepingRepository;
import com.asaproject.asalife.utils.mappers.HousekeepingMapper;
import com.asaproject.asalife.utils.mappers.StatusHousekeepingUserMapper;
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
public class HousekeepingServiceImpl implements HousekeepingService{
    private final HousekeepingRepository housekeepingRepository;
    private final HousekeepingMapper housekeepingMapper;

    @Override
    public List<HousekeepingDto> getAll() {
        return housekeepingMapper.createListHousekeepingDto(housekeepingRepository.findAll());
    }

    @Override
    public List<HousekeepingDto> getAllByUser(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);
        List<Housekeeping> housekeepingList = housekeepingRepository.findAllByUser(user);
        return housekeepingMapper.createListHousekeepingDto(housekeepingList);
    }

    @Override
    public List<HousekeepingDto> addByUser(Principal principal, HousekeepingRequest housekeepingRequest) {
        User user = UserAdminMapper.principalToUser(principal);

        Housekeeping housekeeping = new Housekeeping();
        housekeeping.setDeskripsi(housekeepingRequest.getDeskripsi());
        housekeeping.setLokasi(housekeepingRequest.getLokasi());
        housekeeping.setUser(user);
        housekeepingRepository.save(housekeeping);

        return getAllByUser(principal);
    }

    @Override
    public List<HousekeepingDto> updateStatusHousekeeping(Long id, StatusHousekeeping statusHousekeeping) throws Exception {
        if (!isHousekeepingExist(id)) {
            throw new NotFoundException("HOUSEKEEPING_NOT_FOUND");
        }
        String status = StatusHousekeepingUserMapper.mapStatus(statusHousekeeping.getStatus());

        Housekeeping housekeeping = housekeepingRepository.findHousekeepingByIdNative(id);
        housekeeping.setStatus(status);
        housekeepingRepository.save(housekeeping);

        return getAll();
    }

    @Override
    public Boolean isHousekeepingExist(Long id){
        return !ObjectUtils.isEmpty(housekeepingRepository.findById(id));
    }
}
