package com.asaproject.asalife.domains.models.requests;

import com.asaproject.asalife.domains.EHouseKeepingStatus;
import com.asaproject.asalife.utils.validators.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusHousekeeping {
    @NotNull(message = "Status is mandatory")
    @ValidEnum(enumClass = EHouseKeepingStatus.class, groups = EHouseKeepingStatus.class, message = "Status is not valid")
    private EHouseKeepingStatus status;
}
