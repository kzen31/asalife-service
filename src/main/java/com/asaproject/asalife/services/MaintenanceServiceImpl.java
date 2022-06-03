package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Maintenance;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.MaintenanceOrder;
import com.asaproject.asalife.domains.models.requests.MaintenanceRequest;
import com.asaproject.asalife.domains.models.requests.StatusMaintenance;
import com.asaproject.asalife.domains.models.responses.MaintenanceDto;
import com.asaproject.asalife.repositories.MaintenanceRepository;
import com.asaproject.asalife.utils.mappers.MaintenanceMapper;
import com.asaproject.asalife.utils.mappers.StatusMaintenanceMapper;
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
public class MaintenanceServiceImpl implements MaintenanceService{
    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    public List<MaintenanceDto> getAllMaintenance() {
        return maintenanceMapper.mapMaintenanceDtoToList(maintenanceRepository.findAllByOrderByCreatedAtAsc());
    }

    @Override
    public List<MaintenanceDto> getAllUserMaintenance(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);
        return maintenanceMapper.mapMaintenanceDtoToList(maintenanceRepository.findAllByUserOrderByCreatedAtAsc(user));
    }

    @Override
    public List<MaintenanceDto> getAllPicMaintenance(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);
        return maintenanceMapper.mapMaintenanceDtoToList(maintenanceRepository.findAllByPicNrpOrderByCreatedAtAsc(user.getNrp()));
    }

    @Override
    public void addMaintenance(Principal principal, MaintenanceRequest maintenanceRequest) {
        User user = UserAdminMapper.principalToUser(principal);
        Maintenance maintenance = new Maintenance();

        maintenance.setUser(user);
        maintenance.setLokasi(maintenanceRequest.getLokasi());
        maintenance.setJenisAduan(maintenanceRequest.getJenisaduan());
        maintenanceRepository.save(maintenance);
    }

    @Override
    public void updateOrder(Long id, MaintenanceOrder maintenanceOrder) throws Exception {
        Maintenance maintenance = maintenanceRepository.findMaintenanceByIdNative(id);
        if (ObjectUtils.isEmpty(maintenance) || !ObjectUtils.isEmpty(maintenance.getDeletedAt())) {
            throw new NotFoundException("MAINTENANCE_NOT_FOUND");
        }
        String status = StatusMaintenanceMapper.mapStatus(maintenanceOrder.getStatus());

        maintenance.setPriority(maintenanceOrder.getPriority());
        maintenance.setPicNrp(maintenanceOrder.getPicnrp());
        maintenance.setDuration(maintenanceOrder.getDuration());
        maintenance.setStatus(status);
        maintenanceRepository.save(maintenance);
    }

    @Override
    public void updateOrderStatus(Long id, StatusMaintenance statusMaintenance) throws Exception {
        Maintenance maintenance = maintenanceRepository.findMaintenanceByIdNative(id);
        if (ObjectUtils.isEmpty(maintenance) || !ObjectUtils.isEmpty(maintenance.getDeletedAt())) {
            throw new NotFoundException("MAINTENANCE_NOT_FOUND");
        }
        String status = StatusMaintenanceMapper.mapStatus(statusMaintenance.getStatus());

        maintenance.setStatus(status);
        maintenanceRepository.save(maintenance);
    }

    @Override
    public void cancelOrder(Long id) throws Exception {
        Maintenance maintenance = maintenanceRepository.findMaintenanceByIdNative(id);
        if (ObjectUtils.isEmpty(maintenance) || !ObjectUtils.isEmpty(maintenance.getDeletedAt())) {
            throw new NotFoundException("MAINTENANCE_NOT_FOUND");
        }

        maintenance.setDeletedAt(new Date());
        maintenanceRepository.save(maintenance);
    }

    @Override
    public void deleteMaintenance(Long id) throws Exception {
        Maintenance maintenance = maintenanceRepository.findMaintenanceByIdNative(id);

        if(ObjectUtils.isEmpty(maintenance)) {
            throw new NotFoundException("NOT_FOUND");
        }

        if (!ObjectUtils.isEmpty(maintenance.getDeletedAt())) {
            throw new NotFoundException("NOT_FOUND");
        }
        maintenance.setDeletedAt(new Date());
        maintenanceRepository.save(maintenance);
    }
}
