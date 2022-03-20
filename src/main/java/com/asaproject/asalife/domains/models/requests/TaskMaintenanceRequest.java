package com.asaproject.asalife.domains.models.requests;

import com.asaproject.asalife.domains.ETaskMaintenanceStatus;
import com.asaproject.asalife.utils.validators.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskMaintenanceRequest {
    private String jenisaset;

    private String lokasiaset;

    private String keterangan;

    @ValidEnum(enumClass = ETaskMaintenanceStatus.class, groups = ETaskMaintenanceStatus.class, message = "Status is not valid")
    private ETaskMaintenanceStatus status;
}
