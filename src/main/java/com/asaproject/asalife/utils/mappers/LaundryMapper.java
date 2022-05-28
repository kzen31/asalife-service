package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.Laundry;
import com.asaproject.asalife.domains.entities.RatingCatering;
import com.asaproject.asalife.domains.models.responses.LaundryDto;
import com.asaproject.asalife.domains.models.responses.RatingCateringDto;
import com.asaproject.asalife.repositories.LaundryRepository;
import com.asaproject.asalife.repositories.RatingCateringRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class LaundryMapper {
    private final ModelMapper modelMapper;
    private final LaundryRepository laundryRepository;

    public LaundryDto entityToLaundryDto(Laundry laundry) {
        LaundryDto laundryDto = modelMapper.map(laundry, LaundryDto.class);
        laundryDto.setUserNrp(laundry.getUser().getNrp());
        laundryDto.setUserName(laundry.getUser().getName());
        laundryDto.setTanggal_aduan(laundry.getCreatedAt());
        return laundryDto;
    }

    public List<LaundryDto> createListLaundryDto(List<Laundry> laundryList) {
        List<LaundryDto> laundryDtoList = new ArrayList<LaundryDto>();
        for (Laundry laundry: laundryList) {
            LaundryDto laundryDto = entityToLaundryDto(laundry);
            laundryDtoList.add(laundryDto);
        }
        return laundryDtoList;
    }
}
