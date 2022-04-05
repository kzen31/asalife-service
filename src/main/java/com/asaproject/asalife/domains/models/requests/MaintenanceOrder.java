package com.asaproject.asalife.domains.models.requests;

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
}
