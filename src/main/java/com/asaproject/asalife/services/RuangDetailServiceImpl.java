package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Ruang;
import com.asaproject.asalife.domains.entities.RuangDetail;
import com.asaproject.asalife.domains.models.responses.RuangDetailDto;
import com.asaproject.asalife.repositories.RuangDetailRepository;
import com.asaproject.asalife.repositories.RuangRepository;
import com.asaproject.asalife.utils.mappers.HousekeepingMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RuangDetailServiceImpl implements RuangDetailService{
    private final RuangDetailRepository ruangDetailRepository;
    private final RuangRepository ruangRepository;
    private final HousekeepingMapper housekeepingMapper;

    @Override
    public List<RuangDetailDto> getAllRuangDetail(Long id) throws Exception {
        Ruang ruang = ruangRepository.findRuangByIdNative(id);
        if (ObjectUtils.isEmpty(ruang)) {
            throw new NotFoundException("RUANG_NOT_FOUND");
        }

        return  housekeepingMapper.createListRuangDetailDto(ruangDetailRepository.findAllByRuang_Id(id));
    }

    @Override
    public List<RuangDetailDto> getAllRuangDetail() {
        return housekeepingMapper.createListRuangDetailDto(ruangDetailRepository.findAll());
    }
}
