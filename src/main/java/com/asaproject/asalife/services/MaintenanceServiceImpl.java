package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Maintenance;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.MaintenanceOrder;
import com.asaproject.asalife.domains.models.requests.MaintenanceRequest;
import com.asaproject.asalife.domains.models.requests.StatusMaintenance;
import com.asaproject.asalife.domains.models.responses.MaintenanceDto;
import com.asaproject.asalife.firebase.NotificationData;
import com.asaproject.asalife.firebase.NotificationService;
import com.asaproject.asalife.repositories.MaintenanceRepository;
import com.asaproject.asalife.utils.mappers.MaintenanceMapper;
import com.asaproject.asalife.utils.mappers.StatusMaintenanceMapper;
import com.asaproject.asalife.utils.mappers.UserAdminMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final NotificationService notificationService;

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
    public void addMaintenance(Principal principal, MaintenanceRequest maintenanceRequest) throws Exception {
        User user = UserAdminMapper.principalToUser(principal);
        Maintenance maintenance = new Maintenance();

        maintenance.setUser(user);
        maintenance.setLokasi(maintenanceRequest.getLokasi());
        maintenance.setJenisAduan(maintenanceRequest.getJenisaduan());
        maintenanceRepository.save(maintenance);

        try {
            NotificationData data = new NotificationData("ROLE_GS", "Aduan Maintenance", "ada aduan maintenance baru, mohon segera dilengkapi PIC, schedule, dan priority", "Jenis aduan : " + maintenanceRequest.getJenisaduan());
            notificationService.sendNotificationTopic(data);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void updateComplaintDetail(Long id, MaintenanceOrder maintenanceOrder) throws Exception {
        Maintenance maintenance = maintenanceRepository.findMaintenanceByIdNative(id);
        if (ObjectUtils.isEmpty(maintenance) || !ObjectUtils.isEmpty(maintenance.getDeletedAt())) {
            throw new NotFoundException("MAINTENANCE_NOT_FOUND");
        }
        maintenance.setPriority(maintenanceOrder.getPriority());
        maintenance.setPicNrp(maintenanceOrder.getPicnrp());
        maintenance.setDuration(maintenanceOrder.getDuration());
        maintenanceRepository.save(maintenance);

        try {
            NotificationData data = new NotificationData("ROLE_MT", "Aduan Maintenance", "ada aduan maintenance baru, mohon segera diproses", "Priority aduan : " + maintenanceOrder.getPriority());
            notificationService.sendNotificationTopic(data);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
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

        if (ObjectUtils.isEmpty(maintenance)) {
            throw new NotFoundException("NOT_FOUND");
        }

        if (!ObjectUtils.isEmpty(maintenance.getDeletedAt())) {
            throw new NotFoundException("NOT_FOUND");
        }
        maintenance.setDeletedAt(new Date());
        maintenanceRepository.save(maintenance);
    }

    @Override
    @Scheduled(cron = "0 0 6 * * *")
    public void sendNotificationMTChecker() throws Exception {
        try {
            List<Maintenance> recordMaintenance = maintenanceRepository.findAllByOrderByCreatedAtAsc();
            int expiredAduan = 0;
            int toBeExpiredAduan = 0;

            for (Maintenance maintenance : recordMaintenance) {
                if (ObjectUtils.isEmpty(maintenance.getDuration())) {
                    break;
                }

                DateTime dateRecord = new DateTime(maintenance.getDuration());

                if (dateRecord.isEqual(new DateTime().minus(1)) && !maintenance.getStatus().equals("CLOSED")) {
                    toBeExpiredAduan = toBeExpiredAduan + 1;
                }

                if (dateRecord.isEqual(new DateTime()) && !maintenance.getStatus().equals("CLOSED")) {
                    expiredAduan = expiredAduan + 1;
                }
            }

            String message = "Pemberitahuan masa akhir penyelesaian aduan";
            if (expiredAduan == 0 && toBeExpiredAduan != 0) {
                message = "Besok hari, batas penyelesaian " + toBeExpiredAduan + " aduan akan berakhir";
            } else if (expiredAduan != 0 && toBeExpiredAduan == 0) {
                message = "Hari ini batas penyelesaian " + toBeExpiredAduan + " aduan akan berakhir";
            } else if (expiredAduan != 0 && toBeExpiredAduan != 0) {
                message = "Batas penyelesaian " + toBeExpiredAduan + " aduan akan berakhir hari ini" +
                        " dan batas penyelesaian " + toBeExpiredAduan + " akan berakhir besok";
            }

            NotificationData messageToHCGS = new NotificationData("ROLE_HCGS", "Aduan Maintenance", "pemberitahuan batas akhir penyelesaian aduan", message);
            notificationService.sendNotificationTopic(messageToHCGS);

            NotificationData messageToPROG = new NotificationData("ROLE_PROG", "Aduan Maintenance", "pemberitahuan batas akhir penyelesaian aduan", message);
            notificationService.sendNotificationTopic(messageToPROG);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
