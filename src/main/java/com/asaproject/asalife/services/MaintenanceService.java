package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Maintenance;
import com.asaproject.asalife.domains.models.requests.MaintenanceOrder;
import com.asaproject.asalife.domains.models.requests.MaintenanceRequest;
import com.asaproject.asalife.domains.models.requests.StatusMaintenance;
import com.asaproject.asalife.domains.models.responses.MaintenanceDto;

import java.security.Principal;
import java.util.List;

public interface MaintenanceService {
    List<MaintenanceDto> getAllMaintenance();

    List<MaintenanceDto> getAllUserMaintenance(Principal principal);

    List<MaintenanceDto> getAllPicMaintenance(Principal principal);

    MaintenanceDto addMaintenance(Principal principal, MaintenanceRequest maintenanceRequest);

    MaintenanceDto updateOrder(Long id, MaintenanceOrder maintenanceOrder) throws Exception;

    MaintenanceDto updateOrderStatus(Long id, StatusMaintenance StatusMaintenance) throws Exception;

    void cancelOrder(Long id) throws Exception;
}
