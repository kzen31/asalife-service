package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Housekeeping;
import com.asaproject.asalife.domains.entities.Laundry;
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
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HousekeepingServiceImpl implements HousekeepingService{
    private final HousekeepingRepository housekeepingRepository;
    private final HousekeepingMapper housekeepingMapper;

    @Override
    public List<HousekeepingDto> getAll() {
        return housekeepingMapper.createListHousekeepingDto(housekeepingRepository.findAllByOrderByCreatedAtAsc());
    }

    @Override
    public List<HousekeepingDto> getAllByUser(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);
        List<Housekeeping> housekeepingList = housekeepingRepository.findAllByUserOrderByCreatedAtAsc(user);
        return housekeepingMapper.createListHousekeepingDto(housekeepingList);
    }

    @Override
    public void addByUser(Principal principal, HousekeepingRequest housekeepingRequest) {
        User user = UserAdminMapper.principalToUser(principal);

        Housekeeping housekeeping = new Housekeeping();
        housekeeping.setDeskripsi(housekeepingRequest.getDeskripsi());
        housekeeping.setLokasi(housekeepingRequest.getLokasi());
        housekeeping.setUser(user);
        housekeepingRepository.save(housekeeping);
    }

    @Override
    public void updateStatusHousekeeping(Long id, StatusHousekeeping statusHousekeeping) throws Exception {
        if (!isHousekeepingExist(id)) {
            throw new NotFoundException("HOUSEKEEPING_NOT_FOUND");
        }
        String status = StatusHousekeepingUserMapper.mapStatus(statusHousekeeping.getStatus());

        Housekeeping housekeeping = housekeepingRepository.findHousekeepingByIdNative(id);
        housekeeping.setStatus(status);
        housekeepingRepository.save(housekeeping);
    }

    @Override
    public Boolean isHousekeepingExist(Long id){
        return !ObjectUtils.isEmpty(housekeepingRepository.findById(id));
    }

    @Override
    public void deleteHousekeeping(Long id) throws Exception {
        Housekeeping housekeeping = housekeepingRepository.findHousekeepingByIdNative(id);

        if(ObjectUtils.isEmpty(housekeeping)) {
            throw new NotFoundException("NOT_FOUND");
        }

        if (!ObjectUtils.isEmpty(housekeeping.getDeletedAt())) {
            throw new NotFoundException("NOT_FOUND");
        }
        housekeeping.setDeletedAt(new Date());
        housekeepingRepository.save(housekeeping);
    }
}
