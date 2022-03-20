package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Maintenance;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.MaintenanceOrder;
import com.asaproject.asalife.domains.models.requests.MaintenanceRequest;
import com.asaproject.asalife.domains.models.requests.StatusMaintenance;
import com.asaproject.asalife.repositories.MaintenanceRepository;
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

    @Override
    public List<Maintenance> getAllMaintenance() {
        return maintenanceRepository.findAll();
    }

    @Override
    public List<Maintenance> getAllUserMaintenance(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);
        return maintenanceRepository.findAllByUser(user);
    }

    @Override
    public List<Maintenance> getAllPicMaintenance(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);
        return maintenanceRepository.findAllByPicNrp(user.getNrp());
    }

    @Override
    public Maintenance addMaintenance(Principal principal, MaintenanceRequest maintenanceRequest) {
        User user = UserAdminMapper.principalToUser(principal);
        Maintenance maintenance = new Maintenance();

        maintenance.setUser(user);
        maintenance.setLokasi(maintenanceRequest.getLokasi());
        maintenance.setJenisAduan(maintenanceRequest.getJenisaduan());
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance updateOrder(Long id, MaintenanceOrder maintenanceOrder) throws Exception {
        Maintenance maintenance = maintenanceRepository.findMaintenanceByIdNative(id);
        if (ObjectUtils.isEmpty(maintenance)) {
            throw new NotFoundException("MAINTENANCE_NOT_FOUND");
        }

        maintenance.setPriority(maintenanceOrder.getPriority());
        maintenance.setPicNrp(maintenanceOrder.getPicnrp());
        maintenance.setDuration(maintenanceOrder.getDuration());
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance updateOrderStatus(Long id, StatusMaintenance statusMaintenance) throws Exception {
        Maintenance maintenance = maintenanceRepository.findMaintenanceByIdNative(id);
        if (ObjectUtils.isEmpty(maintenance)) {
            throw new NotFoundException("MAINTENANCE_NOT_FOUND");
        }
        String status = StatusMaintenanceMapper.mapStatus(statusMaintenance.getStatus());

        maintenance.setStatus(status);
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance cancelOrder(Long id) throws Exception {
        Maintenance maintenance = maintenanceRepository.findMaintenanceByIdNative(id);
        if (ObjectUtils.isEmpty(maintenance)) {
            throw new NotFoundException("MAINTENANCE_NOT_FOUND");
        }

        maintenance.setDeletedAt(new Date());
        return maintenanceRepository.save(maintenance);
    }
}
