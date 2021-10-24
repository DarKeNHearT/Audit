package com.varosa.audit.service.impl;


import com.varosa.audit.model.Branch;
import com.varosa.audit.model.Role;
import com.varosa.audit.model.User;
import com.varosa.audit.pojo.UserPojo;
import com.varosa.audit.repository.RepositoryBranch;
import com.varosa.audit.repository.RepositoryUser;
import com.varosa.audit.repository.RoleRepository;
import com.varosa.audit.service.BranchService;
import com.varosa.audit.service.RoleService;
import com.varosa.audit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RepositoryBranch repositoryBranch;
    @Autowired
    RepositoryUser repositoryUser;
    @Autowired
    RoleService roleService;
    @Autowired
    BranchService branchService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Transactional
    @Override
    public UserPojo insert(UserPojo userPojo) throws Exception {
        User user;
        if(userPojo.getId() != null) {
            user = checkit(userPojo.getId());
        }
        else {
            user = new User();
        }
        user.setStatus(userPojo.isStatus());
        user.setEmail(userPojo.getEmail());
        Role role = roleService.checkit(userPojo.getRoleId());
        Collection<Role> roleCollection = new ArrayList<>();
        roleCollection.add(role);
        user.setRoles(roleCollection);
        Branch branch = branchService.checkit(userPojo.getBranchId());
        user.setBranchId(branch);
        user.setPassword(bCryptPasswordEncoder.encode(userPojo.getPassword()));
        User user1 = repositoryUser.save(user);
        userPojo.setId(user1.getId());
        return userPojo;
    }

    @Override
    public User checkit(Long id) throws Exception {
        Optional<User> user = repositoryUser.findById(id);
        if(!user.isPresent()) {
            throw new Exception("User Not present");
        }
        else {
            return user.get();
        }
    }

    @Override
    public List<Map<String, Object>> getAll() {
        return repositoryUser.getAll();
    }

    @Override
    public void delete(Long id) throws Exception {
        checkit(id);
        repositoryUser.delete(new User(id));
    }

    @Override
    public Map<String, Object> getById(Long id) throws Exception {
        checkit(id);
        return repositoryUser.getById(id);
    }
}
