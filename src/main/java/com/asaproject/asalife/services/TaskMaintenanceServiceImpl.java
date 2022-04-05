package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.ETaskMaintenanceStatus;
import com.asaproject.asalife.domains.entities.TaskMaintenance;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.TaskMaintenanceRequest;
import com.asaproject.asalife.domains.models.responses.TaskMaintenanceDto;
import com.asaproject.asalife.repositories.TaskMaintenanceRepository;
import com.asaproject.asalife.utils.mappers.StatusTaskMaintenanceMapper;
import com.asaproject.asalife.utils.mappers.TaskMaintenanceMapper;
import com.asaproject.asalife.utils.mappers.UserAdminMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskMaintenanceServiceImpl implements TaskMaintenanceService{
    private final TaskMaintenanceRepository taskMaintenanceRepository;
    private final TaskMaintenanceMapper taskMaintenanceMapper;

    @Override
    public List<TaskMaintenanceDto> getAllTask() {
        return taskMaintenanceMapper.mapTaskMaintenanceDtoToList(taskMaintenanceRepository.findAllByOrderByCreatedAtAsc());
    }

    @Override
    public List<TaskMaintenanceDto> getMyTask(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);

        return taskMaintenanceMapper.mapTaskMaintenanceDtoToList(taskMaintenanceRepository.findAllByUserOrderByCreatedAtAsc(user));
    }

    @Override
    public List<TaskMaintenanceDto> addTask(Principal principal, TaskMaintenanceRequest taskMaintenanceRequest) {
        User user = UserAdminMapper.principalToUser(principal);
        String status = StatusTaskMaintenanceMapper.mapStatus(taskMaintenanceRequest.getStatus());

        TaskMaintenance taskMaintenance = new TaskMaintenance();
        taskMaintenance.setUser(user);
        taskMaintenance.setJenisAset(taskMaintenanceRequest.getJenisaset());
        taskMaintenance.setLokasiAset(taskMaintenanceRequest.getLokasiaset());
        taskMaintenance.setKeterangan(taskMaintenanceRequest.getKeterangan());
        taskMaintenance.setStatus(status);
        taskMaintenanceRepository.save(taskMaintenance);

        return getMyTask(principal);
    }

    @Override
    public TaskMaintenanceDto getInfoTask(Long id) throws Exception {
        TaskMaintenance taskMaintenance = taskMaintenanceRepository.findTaskMaintenanceByIdNative(id);

        if (ObjectUtils.isEmpty(taskMaintenance) || !ObjectUtils.isEmpty(taskMaintenance.getDeletedAt())) {
            throw new NotFoundException("TASK_NOT_FOUND");
        }

        return taskMaintenanceMapper.entityToTaskMaintenanceDto(taskMaintenance);
    }

    @Override
    public TaskMaintenanceDto updateStatusTask(Long id, ETaskMaintenanceStatus eTaskMaintenanceStatus) throws Exception {
        TaskMaintenance taskMaintenance = taskMaintenanceRepository.findTaskMaintenanceByIdNative(id);

        if (ObjectUtils.isEmpty(taskMaintenance) || !ObjectUtils.isEmpty(taskMaintenance.getDeletedAt())) {
            throw new NotFoundException("TASK_NOT_FOUND");
        }
        String status = StatusTaskMaintenanceMapper.mapStatus(eTaskMaintenanceStatus);
        taskMaintenance.setStatus(status);
        return taskMaintenanceMapper.entityToTaskMaintenanceDto(taskMaintenanceRepository.save(taskMaintenance));
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        TaskMaintenance taskMaintenance = taskMaintenanceRepository.findTaskMaintenanceByIdNative(id);

        if (ObjectUtils.isEmpty(taskMaintenance) || !ObjectUtils.isEmpty(taskMaintenance.getDeletedAt())) {
            throw new NotFoundException("TASK_NOT_FOUND");
        }

        taskMaintenance.setDeletedAt(new Date());
        taskMaintenanceRepository.save(taskMaintenance);
    }
}
