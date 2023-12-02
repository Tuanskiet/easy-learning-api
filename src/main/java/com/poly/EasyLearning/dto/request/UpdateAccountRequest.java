package com.poly.EasyLearning.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {
    private String oldUsername;
    private UserRequest userUpdate;
}
