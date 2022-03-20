package com.asaproject.asalife.domains.models.responses;

import com.asaproject.asalife.domains.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CateringDto {
    private long id;
    private String userName;
    private String userNrp;
    private String lokasi;
    private String deskripsi;
    private String kritik_saran;
    private String status;
}
