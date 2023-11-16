package com.poly.EasyLearning.service;

import com.poly.EasyLearning.entity.RoleApp;
import com.poly.EasyLearning.enums.RoleName;
import com.poly.EasyLearning.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepo roleRepo;

    public RoleApp create(RoleApp role){
        if(findRole(role.getName()).isPresent()){
            throw new RuntimeException("Role already exist!");
        }
        return roleRepo.save(role);
    }

    public Optional<RoleApp> findRole(RoleName roleName){
        return roleRepo.findByName(roleName);
    }
}
