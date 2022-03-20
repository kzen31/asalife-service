package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Maintenance;
import com.asaproject.asalife.domains.models.requests.MaintenanceOrder;
import com.asaproject.asalife.domains.models.requests.MaintenanceRequest;
import com.asaproject.asalife.domains.models.requests.StatusMaintenance;

import java.security.Principal;
import java.util.List;

public interface MaintenanceService {
    List<Maintenance> getAllMaintenance();

    List<Maintenance> getAllUserMaintenance(Principal principal);

    List<Maintenance> getAllPicMaintenance(Principal principal);

    Maintenance addMaintenance(Principal principal, MaintenanceRequest maintenanceRequest);

    Maintenance updateOrder(Long id, MaintenanceOrder maintenanceOrder) throws Exception;

    Maintenance updateOrderStatus(Long id, StatusMaintenance StatusMaintenance) throws Exception;

    Maintenance cancelOrder(Long id) throws Exception;
}
