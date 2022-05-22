package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.models.interfaces.CountByMonth;
import com.asaproject.asalife.domains.models.responses.CountByMonthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CountByMonthMapper {

    public CountByMonthDto interfaceToCountByMonthDto(CountByMonth countByMonth) {
        CountByMonthDto count = new CountByMonthDto();
        count.setTahun(countByMonth.getTahun());
        count.setBulan(countByMonth.getBulan());
        count.setTotal(countByMonth.getTotal());
        return count;
    }

    public List<CountByMonthDto> createListCountByMonthDto(List<CountByMonth> countByMonthList) {
        List<CountByMonthDto> countByMonthDtoList = new ArrayList<>();
        for (CountByMonth countByMonth: countByMonthList) {
            CountByMonthDto countByMonthDto = interfaceToCountByMonthDto(countByMonth);
            countByMonthDtoList.add(countByMonthDto);
        }
        return countByMonthDtoList;
    }
}
