package com.asaproject.asalife.domains.models.requests;

import com.asaproject.asalife.domains.ECateringStatus;
import com.asaproject.asalife.domains.ELaundryStatus;
import com.asaproject.asalife.utils.validators.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusLaundry {
    @NotEmpty(message = "Status is mandatory")
    @ValidEnum(enumClass = ECateringStatus.class, groups = ECateringStatus.class, message = "Status is not valid")
    private ELaundryStatus status;
}
