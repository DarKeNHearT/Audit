package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.pojo.UserPojo;
import com.example.demo.repository.RepositoryBranch;
import com.example.demo.repository.RepositoryUser;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RepositoryBranch repositoryBranch;
    @Autowired
    RepositoryUser repositoryUser;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Transactional
    @Override
    public User insert(UserPojo userPojo) throws Exception {
        User user;
        if(userPojo.getId() != null) {
            user = checkit(userPojo.getId());
        }
        else {
            user = new User();
        }

        user.setStatus(userPojo.isStatus());
        user.setEmail(userPojo.getEmail());
        Long roleId = roleRepository.getIdByRoleName(userPojo.getRoleName());
        if(roleId == null)
            throw new Exception("Role Not Present");
        Optional<Role> role = roleRepository.findById(roleId);
        Collection<Role> roleCollection = new ArrayList<>();
        roleCollection.add(role.get());
        user.setRoles(roleCollection);

        Long branchId = repositoryBranch.getIdByName(userPojo.getBranchName());


        if(branchId == null)
            throw new Exception("Branch Not Found");
        Optional<Branch> branch = repositoryBranch.findById(branchId);

        user.setBranchId(branch.get());
        user.setPassword(bCryptPasswordEncoder.encode(userPojo.getPassword()));

        return repositoryUser.save(user);
    }

    @Override
    public User checkit(Long id) throws Exception {
        Optional<User> user = repositoryUser.findById(id);
        if(!user.isPresent()) {
            throw new Exception("User Not present");
        }
        else if(user.isPresent()){
            throw new Exception("User Already Present");
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
    public void delete(Long id) {
        repositoryUser.delete(new User(id));
    }

    @Override
    public Map<String, Object> getById(Long id) {
        return repositoryUser.getById(id);
    }
}
