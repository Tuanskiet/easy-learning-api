package com.poly.EasyLearning.dto.request;

import com.poly.EasyLearning.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {
    private String oldUsername;
    private UserRequest userUpdate;
    private List<RoleName> roles;
}
