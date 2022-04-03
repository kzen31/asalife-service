package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.RecordHousekeeping;
import com.asaproject.asalife.domains.entities.RuangDetail;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.RecordHousekeepingRequest;
import com.asaproject.asalife.domains.models.responses.RecordHousekeepingDto;
import com.asaproject.asalife.domains.models.responses.RecordResponse;
import com.asaproject.asalife.repositories.RecordHousekeepingRepository;
import com.asaproject.asalife.repositories.RuangDetailRepository;
import com.asaproject.asalife.repositories.UserRepository;
import com.asaproject.asalife.utils.mappers.HousekeepingMapper;
import com.asaproject.asalife.utils.mappers.UserAdminMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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
public class RecordHousekeepingServiceImpl implements RecordHousekeepingService{
    private final RecordHousekeepingRepository recordHousekeepingRepository;
    private final UserRepository userRepository;
    private final RuangDetailRepository ruangDetailRepository;
    private final HousekeepingMapper housekeepingMapper;

    @Override
    public List<RecordResponse> getAllRecord() {
        return housekeepingMapper.createTemplateRecordList(recordHousekeepingRepository.findAllAndOrder());
    }

    @Override
    public List<RecordResponse> getMyRecord(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);
        List<RecordHousekeeping> recordHousekeeping = recordHousekeepingRepository.findAllByUserAndOrder(user.getId());
        return housekeepingMapper.createTemplateRecordList(recordHousekeeping);
    }

    @Override
    public List<RecordResponse> getAllByUser(String nrp) throws Exception {
        User user = userRepository.findByNrp(nrp);
        if (ObjectUtils.isEmpty(user)) {
            throw new NotFoundException("USER_NOT_FOUND");
        }
        List<RecordHousekeeping> recordHousekeeping = recordHousekeepingRepository.findAllByUserAndOrder(user.getId());
        return housekeepingMapper.createTemplateRecordList(recordHousekeeping);
    }

    @Override
    public void addRecord(Principal principal, Long idRuangDetail, RecordHousekeepingRequest recordHousekeepingRequest) throws Exception {
        User user = UserAdminMapper.principalToUser(principal);
        RuangDetail ruangDetail = ruangDetailRepository.findRuangDetailByIdNative(idRuangDetail);
        if(ObjectUtils.isEmpty(ruangDetail)) {
            throw new NotFoundException("RUANG_DETAIL_NOT_FOUND");
        }

        boolean ceklis = Boolean.parseBoolean(recordHousekeepingRequest.getCeklis());

        RecordHousekeeping record = new RecordHousekeeping();
        record.setUser(user);
        record.setRuangDetail(ruangDetail);
        record.setCeklis(ceklis);
        recordHousekeepingRepository.save(record);
    }

    @Override
    public void verifyRecordStatus(Long idRecord, RecordHousekeepingRequest recordHousekeepingRequest) throws Exception{
        RecordHousekeeping record = recordHousekeepingRepository.findRecordByIdNative(idRecord);
        if (ObjectUtils.isEmpty(record)) {
            throw new NotFoundException("RECORD_NOT_FOUND");
        }

        boolean ceklis = Boolean.parseBoolean(recordHousekeepingRequest.getCeklis());

        record.setCeklis(ceklis);
        recordHousekeepingRepository.save(record);
    }
}
