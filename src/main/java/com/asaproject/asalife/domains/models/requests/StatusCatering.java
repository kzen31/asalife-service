package com.asaproject.asalife.domains.models.requests;

import com.asaproject.asalife.domains.ECateringStatus;
import com.asaproject.asalife.utils.validators.ValidEnum;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatusCatering {
    @NotBlank(message = "Status is mandatory")
    @ValidEnum(enumClass = ECateringStatus.class, groups = ECateringStatus.class, message = "Status is not valid")
    private ECateringStatus status;
}
