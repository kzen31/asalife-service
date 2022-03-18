package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.Housekeeping;
import com.asaproject.asalife.domains.entities.RuangDetail;
import com.asaproject.asalife.domains.models.responses.HousekeepingDto;
import com.asaproject.asalife.domains.models.responses.RuangDetailDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class HousekeepingMapper {
    private final ModelMapper modelMapper;

    public HousekeepingDto entityToHousekeepingDto(Housekeeping housekeeping) {
        HousekeepingDto housekeepingDto = modelMapper.map(housekeeping, HousekeepingDto.class);
        housekeepingDto.setUserNrp(housekeeping.getUser().getNrp());
        housekeepingDto.setUserName(housekeeping.getUser().getName());
        return housekeepingDto;
    }

    public List<HousekeepingDto> createListHousekeepingDto(List<Housekeeping> housekeepingList) {
        List<HousekeepingDto> housekeepingDtoList = new ArrayList<HousekeepingDto>();
        for (Housekeeping housekeeping: housekeepingList) {
            HousekeepingDto housekeepingDto = entityToHousekeepingDto(housekeeping);
            housekeepingDtoList.add(housekeepingDto);
        }
        return housekeepingDtoList;
    }

    public RuangDetailDto entityToRuangDetailDto(RuangDetail ruangDetail) {
        RuangDetailDto ruangDetailDto = modelMapper.map(ruangDetail, RuangDetailDto.class);
        ruangDetailDto.setRuang_name(ruangDetail.getRuang().getName());
        return ruangDetailDto;
    }

    public List<RuangDetailDto> createListRuangDetailDto(List<RuangDetail> ruangDetailList) {
        List<RuangDetailDto> ruangDetailDtoList = new ArrayList<RuangDetailDto>();
        for (RuangDetail ruangDetail: ruangDetailList) {
            RuangDetailDto ruangDetailDto = entityToRuangDetailDto(ruangDetail);
            ruangDetailDtoList.add(ruangDetailDto);
        }
        return ruangDetailDtoList;
    }
}
