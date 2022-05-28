package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.Maintenance;
import com.asaproject.asalife.domains.models.responses.MaintenanceDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class MaintenanceMapper {
    private final ModelMapper modelMapper;

    public MaintenanceDto entityToMaintenanceDto(Maintenance maintenance) {
        MaintenanceDto maintenanceDto = modelMapper.map(maintenance, MaintenanceDto.class);
        maintenanceDto.setUserName(maintenance.getUser().getName());
        maintenanceDto.setUserNrp(maintenance.getUser().getNrp());
        maintenanceDto.setTanggalAduan(maintenance.getCreatedAt());
        return maintenanceDto;
    }

    public List<MaintenanceDto> mapMaintenanceDtoToList (List<Maintenance> maintenances) {
        List<MaintenanceDto> maintenanceDtoList = new ArrayList<MaintenanceDto>();

        for (Maintenance maintenance : maintenances) { // (int i = 0; i < list.size(); i++)
            MaintenanceDto maintenanceDto = entityToMaintenanceDto(maintenance);
            maintenanceDtoList.add(maintenanceDto);
        }
        return maintenanceDtoList;
    }
}
