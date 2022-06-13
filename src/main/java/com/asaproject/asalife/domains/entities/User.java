package com.asaproject.asalife.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
public class User extends Auditable implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "Name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "Nrp is mandatory")
    @Column(name = "nrp", nullable = false, unique = true)
    private String nrp;

    @JsonIgnore
    @NotEmpty(message = "Password is mandatory")
    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Lob
    @Column(name = "department")
    private String department;

    @JsonIgnore
    @Column(name = "is_not_expired", nullable = false)
    private Boolean isNotExpired = true;

    @JsonIgnore
    @Column(name = "is_not_locked", nullable = false)
    private Boolean isNotLocked = true;

    @JsonIgnore
    @Column(name = "is_credentials_not_expired", nullable = false)
    private Boolean isCredentialsNotExpired = true;

    @JsonIgnore
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return nrp;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isNotExpired;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return isNotLocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNotExpired;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    // Forget Pass here
    @JsonIgnore
    private String verifyToken;

    @JsonIgnore
    @Column(length = 100)
    private String otp;

    @JsonIgnore
    private LocalDateTime otpExpiredDate;

    // add one to many refresh token
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<RefreshToken> refreshTokens;

    // add one to many catering
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Catering> catering;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<RatingCatering> ratingCaterings;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<RatingCateringMany> ratingCateringManies;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Maintenance> maintenanceList;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<TaskMaintenance> taskMaintenanceList;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Laundry> laundries;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Housekeeping> houseeepings;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<RecordHousekeeping> recordHousekeepings;
}