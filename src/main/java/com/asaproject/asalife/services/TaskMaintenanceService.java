package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.ETaskMaintenanceStatus;
import com.asaproject.asalife.domains.models.requests.TaskMaintenanceRequest;
import com.asaproject.asalife.domains.models.responses.TaskMaintenanceDto;

import java.security.Principal;
import java.util.List;

public interface TaskMaintenanceService {
    List<TaskMaintenanceDto> getAllTask();

    List<TaskMaintenanceDto> getMyTask(Principal principal);

    List<TaskMaintenanceDto> addTask(Principal principal, TaskMaintenanceRequest taskMaintenanceRequest);

    TaskMaintenanceDto getInfoTask(Long id) throws Exception;

    TaskMaintenanceDto updateStatusTask(Long id, ETaskMaintenanceStatus eTaskMaintenanceStatus) throws Exception;

    void deleteTask(Long id) throws Exception;
}
