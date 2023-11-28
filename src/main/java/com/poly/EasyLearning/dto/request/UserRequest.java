package com.poly.EasyLearning.dto.request;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.EasyLearning.enums.Provider;
import com.poly.EasyLearning.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String avatar = "https://i.imgur.com/6VBx3io.png";
    private Provider provider = Provider.LOCAL;
    private RoleName role = RoleName.ROLE_USER;
}
