package com.poly.EasyLearning.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private Integer accountId;
    private String fullName;
    private String email;
    private String username;
    private String provider;
    private List<String> roles;
    private Boolean enable;
    private Boolean locked;
}