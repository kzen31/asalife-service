package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.Mess;
import com.asaproject.asalife.domains.models.responses.MessDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class MessMapper {
    private final ModelMapper modelMapper;

    public MessDto entityToMessDto(Mess mess) {
        MessDto messDto = modelMapper.map(mess, MessDto.class);
        return messDto;
    }

    public List<MessDto> mapMessDtoToList (List<Mess> messList) {
        List<MessDto> messDtoList = new ArrayList<>();

        for (Mess mess : messList) { // (int i = 0; i < list.size(); i++)
            MessDto messDto = entityToMessDto(mess);
            messDtoList.add(messDto);
        }
        return messDtoList;
    }
}
