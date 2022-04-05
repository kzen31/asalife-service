package com.asaproject.asalife.domains.models.requests;

import com.asaproject.asalife.domains.ETaskMaintenanceStatus;
import com.asaproject.asalife.utils.validators.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskMaintenanceRequest {
    @NotEmpty(message = "jenisaset required")
    private String jenisaset;

    @NotEmpty(message = "lokasiaset required")
    private String lokasiaset;

    @NotEmpty(message = "keterangan required")
    private String keterangan;

    @NotNull(message = "status required")
    @ValidEnum(enumClass = ETaskMaintenanceStatus.class, groups = ETaskMaintenanceStatus.class, message = "Status is not valid")
    private ETaskMaintenanceStatus status;
}
