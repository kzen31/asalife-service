package com.asaproject.asalife.domains.models.requests;

import com.asaproject.asalife.domains.EMaintenanceStatus;
import com.asaproject.asalife.utils.validators.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceOrder {
    @NotEmpty(message = "Priority Required")
    private String priority;

    @NotNull(message = "Duration Not null")
    private Date duration;

    @NotEmpty(message = "picnrp Required")
    private String picnrp;

    @NotNull(message = "Status is mandatory")
    @ValidEnum(enumClass = EMaintenanceStatus.class, groups = EMaintenanceStatus.class, message = "Status is not valid")
    private EMaintenanceStatus status;
}
