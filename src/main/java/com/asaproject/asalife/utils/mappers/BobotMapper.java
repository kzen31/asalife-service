package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.Bobot;
import com.asaproject.asalife.domains.models.responses.BobotDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class BobotMapper {
    private final ModelMapper modelMapper;

    public BobotDto entityToBobotDto(Bobot bobot) {
        BobotDto bobotDto = modelMapper.map(bobot, BobotDto.class);
        return bobotDto;
    }

    public List<BobotDto> mapBobotDtoToList (List<Bobot> bobotList) {
        List<BobotDto> bobotDtoList = new ArrayList<>();

        for (Bobot bobot : bobotList) { // (int i = 0; i < list.size(); i++)
            BobotDto bobotDto = entityToBobotDto(bobot);
            bobotDtoList.add(bobotDto);
        }
        return bobotDtoList;
    }
}
