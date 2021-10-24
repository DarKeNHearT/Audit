package com.varosa.audit.service.impl;

import com.varosa.audit.model.Role;
import com.varosa.audit.repository.RoleRepository;
import com.varosa.audit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository repositoryRole;
    @Override
    public Role checkit(Long id) throws Exception {
        Optional<Role> role = repositoryRole.findById(id);
        if(!role.isPresent())
            throw new Exception("Role Not present");
        return role.get();
    }
}
