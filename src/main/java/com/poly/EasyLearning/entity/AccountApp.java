package com.poly.EasyLearning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.EasyLearning.enums.Provider;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "[account_app]")
public class AccountApp implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String  username;

    @JsonIgnore
    @Column(name="password" ,length = 1024)
    private String  password;

    @Enumerated(EnumType.STRING)
    private Provider provider;
    private boolean locked = false;
    private boolean enable = true;

    @JsonProperty("userInfo")
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private UserInfo userApp;

//    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleApp> roles = new HashSet<>();

    public AccountApp(String username, String password, Provider provider) {
        this.username = username;
        this.password = password;
        this.provider = provider;
    }

//    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (RoleApp role: this.roles) {
            authorityList.add(new SimpleGrantedAuthority(role.getName().getValue()));
        }
        return authorityList;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }


}
