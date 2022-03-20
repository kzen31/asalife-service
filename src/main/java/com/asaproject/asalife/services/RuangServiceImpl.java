package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Ruang;
import com.asaproject.asalife.domains.entities.RuangDetail;
import com.asaproject.asalife.domains.models.responses.RuangDto;
import com.asaproject.asalife.repositories.RuangDetailRepository;
import com.asaproject.asalife.repositories.RuangRepository;
import com.asaproject.asalife.utils.mappers.HousekeepingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RuangServiceImpl implements RuangService{
    private final RuangRepository ruangRepository;
    private final RuangDetailRepository ruangDetailRepository;
    private final HousekeepingMapper housekeepingMapper;

    @Override
    public List<RuangDto> getAllRuang() {
        List<RuangDetail> list = ruangDetailRepository.findAll();
        return housekeepingMapper.createTemplateRuangDtoList(list);
    }
}
