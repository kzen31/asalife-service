package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.Housekeeping;
import com.asaproject.asalife.domains.entities.RecordHousekeeping;
import com.asaproject.asalife.domains.entities.RuangDetail;
import com.asaproject.asalife.domains.models.responses.HousekeepingDto;
import com.asaproject.asalife.domains.models.responses.RecordHousekeepingDto;
import com.asaproject.asalife.domains.models.responses.RecordResponse;
import com.asaproject.asalife.domains.models.responses.RuangDetailDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public RecordHousekeepingDto entityToRecordHousekeepingDto(RecordHousekeeping recordHousekeeping) {
        RecordHousekeepingDto recordHousekeepingDto = modelMapper.map(recordHousekeeping, RecordHousekeepingDto.class);
        recordHousekeepingDto.setRuangDetail(recordHousekeeping.getRuangDetail().getDetail());
        recordHousekeepingDto.setRuang(recordHousekeeping.getRuangDetail().getRuang().getName());
        return recordHousekeepingDto;
    }

    public List<RecordHousekeepingDto> createListRecordHousekeepingDto(List<RecordHousekeeping> recordHousekeepingList) {
        List<RecordHousekeepingDto> recordHousekeepingDtoList = new ArrayList<RecordHousekeepingDto>();
        for (RecordHousekeeping recordHousekeeping: recordHousekeepingList) {
            RecordHousekeepingDto recordHousekeepingDto = entityToRecordHousekeepingDto(recordHousekeeping);
            recordHousekeepingDtoList.add(recordHousekeepingDto);
        }
        return recordHousekeepingDtoList;
    }

    public List<RecordResponse> createTemplateRecordList(List<RecordHousekeeping> recordHousekeeping) {
        List<RecordResponse> recordResponseList = new ArrayList<RecordResponse>();
        int i = 0;
        while (i < recordHousekeeping.size()) {
            List<RecordHousekeepingDto> recordDtoList = new ArrayList<RecordHousekeepingDto>();
            RecordResponse recordResponse = new RecordResponse();

            Integer lengthRecordDtoList = checkrecordDtoSize (i,  recordHousekeeping);
            recordResponse.setUserName(recordHousekeeping.get(i).getUser().getName());
            recordResponse.setUserNrp(recordHousekeeping.get(i).getUser().getNrp());
            int loopingRecord = 0;
            while (loopingRecord < lengthRecordDtoList) {
                recordDtoList.add(entityToRecordHousekeepingDto(recordHousekeeping.get(i)));
                i++;
                loopingRecord++;
            }
            recordResponse.setRecordDtoList(recordDtoList);
            recordResponseList.add(recordResponse);
        }
        return recordResponseList;
    }

    public Integer checkrecordDtoSize (Integer index, List<RecordHousekeeping> recordHousekeepings) {
        Integer valueLength = 1;
        while (index < recordHousekeepings.size()) {
            if (isNextValueHasSameUserAndDay(index, recordHousekeepings)) {
                valueLength++;
            } else {
                break;
            }
            index++;
        }
        return valueLength;
    }

    public Boolean isNextValueHasSameUserAndDay(Integer index, List<RecordHousekeeping> recordHousekeepings) {
        if ((index + 1) == recordHousekeepings.size()) {
            return false;
        }
        boolean checkName = Objects.equals(recordHousekeepings.get(index).getUser().getName(), recordHousekeepings.get(index + 1).getUser().getName());
        String timeA = recordHousekeepings.get(index).getCreatedAt().toLocalDate().toString();
        String timeB = recordHousekeepings.get(index + 1).getCreatedAt().toLocalDate().toString();

        boolean checkDate = Objects.equals(timeA, timeB);
        return checkName == checkDate;
    }
};
