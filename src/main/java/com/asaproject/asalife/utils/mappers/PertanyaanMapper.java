package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.models.responses.BobotDto;
import com.asaproject.asalife.domains.models.responses.PertanyaanDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class PertanyaanMapper {
    private final ModelMapper modelMapper;
    private final BobotMapper bobotMapper;

    public PertanyaanDto entityToPertanyaanDto(Pertanyaan pertanyaan) {
        PertanyaanDto pertanyaanDto = modelMapper.map(pertanyaan, PertanyaanDto.class);
        pertanyaanDto.setBobots(bobotMapper.mapBobotDtoToList(pertanyaan.getBobots()));
        return pertanyaanDto;
    }

    public List<PertanyaanDto> mapPertanyaanDtoToList (List<Pertanyaan> pertanyaanList) {
        List<PertanyaanDto> pertanyaanDtoList = new ArrayList<>();

        for (Pertanyaan pertanyaan : pertanyaanList) { // (int i = 0; i < list.size(); i++)
            PertanyaanDto pertanyaanDto = entityToPertanyaanDto(pertanyaan);
            pertanyaanDtoList.add(pertanyaanDto);
        }
        return pertanyaanDtoList;
    }
}
