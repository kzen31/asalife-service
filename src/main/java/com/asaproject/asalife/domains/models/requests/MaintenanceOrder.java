package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceOrder {
    private String priority;
    private Date duration;
    private String picnrp;
}
