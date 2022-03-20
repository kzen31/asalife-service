package com.asaproject.asalife.domains.models.requests;

import com.asaproject.asalife.domains.ETaskMaintenanceStatus;
import com.asaproject.asalife.utils.validators.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusTaskMaintenance {
    @NotEmpty(message = "Status is mandatory")
    @ValidEnum(enumClass = ETaskMaintenanceStatus.class, groups = ETaskMaintenanceStatus.class, message = "Status is not valid")
    private ETaskMaintenanceStatus status;
}
