package com.project.musicwebbe.entities.permission;

import com.project.musicwebbe.entities.Favorite;
import com.project.musicwebbe.entities.Playlist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_users", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_USER_UK", columnNames = "email"),
                @UniqueConstraint(name = "APP_USER_CODE_UK", columnNames = "user_code")})
public class AppUser implements Serializable {
    private static final long serialVersionUID = 2L;

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password", length = 128)
    private String password;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn (name = "user_id") },
            inverseJoinColumns = { @JoinColumn (name = "role_id")})
    private Set<AppRole> roles;

    @OneToMany(mappedBy = "appUser")
    private List<Playlist> playlists;

    @OneToMany(mappedBy = "appUser")
    private List<Favorite> favorites;

    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Column(name = "enabled", length = 1)
    private Boolean enabled;

    public AppUser(String email, String password, Set<AppRole> roles, String userCode, LocalDateTime dateCreate,
                   String fullName, Integer gender, LocalDate dateOfBirth, String phoneNumber, String address,
                   Boolean accountNonExpired, Boolean accountNonLocked, Boolean credentialsNonExpired, Boolean enabled) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.userCode = userCode;
        this.dateCreate = dateCreate;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

}