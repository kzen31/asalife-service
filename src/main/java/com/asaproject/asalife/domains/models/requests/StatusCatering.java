package com.asaproject.asalife.domains.models.requests;

import com.asaproject.asalife.domains.ECateringStatus;
import com.asaproject.asalife.utils.validators.ValidEnum;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatusCatering {
    @NotNull(message = "Name is mandatory")
    @ValidEnum(enumClass = ECateringStatus.class, groups = ECateringStatus.class, message = "Status is not valid")
    private ECateringStatus status;
}
