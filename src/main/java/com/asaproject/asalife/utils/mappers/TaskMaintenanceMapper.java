package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.TaskMaintenance;
import com.asaproject.asalife.domains.models.responses.TaskMaintenanceDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class TaskMaintenanceMapper {
    private final ModelMapper modelMapper;

    public TaskMaintenanceDto entityToTaskMaintenanceDto(TaskMaintenance taskMaintenance) {
        TaskMaintenanceDto taskMaintenanceDto = modelMapper.map(taskMaintenance, TaskMaintenanceDto.class);
        taskMaintenanceDto.setUserNrp(taskMaintenance.getUser().getNrp());
        taskMaintenanceDto.setUserName(taskMaintenance.getUser().getName());
        taskMaintenanceDto.setCreated_at(taskMaintenance.getCreatedAt());
        return taskMaintenanceDto;
    }

    public List<TaskMaintenanceDto> mapTaskMaintenanceDtoToList (List<TaskMaintenance> taskMaintenanceList) {
        List<TaskMaintenanceDto> taskMaintenanceDtoList = new ArrayList<TaskMaintenanceDto>();

        for (TaskMaintenance taskMaintenance : taskMaintenanceList) { // (int i = 0; i < list.size(); i++)
            TaskMaintenanceDto taskMaintenanceDto = entityToTaskMaintenanceDto(taskMaintenance);
            taskMaintenanceDtoList.add(taskMaintenanceDto);
        }
        return taskMaintenanceDtoList;
    }

}
