package com.asaproject.asalife.domains.models.responses;

import com.asaproject.asalife.domains.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyProfile {
    private Long id;
    private String name;
    private String nrp;
    private Collection<Role> roles = new ArrayList<>();
    private String phoneNumber;
    private String department;
}
