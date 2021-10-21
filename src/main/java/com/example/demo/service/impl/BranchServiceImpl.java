package com.example.demo.service.impl;

import com.example.demo.model.Branch;
import com.example.demo.pojo.BranchPojo;
import com.example.demo.repository.RepositoryBranch;
import com.example.demo.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {
    @Autowired
    RepositoryBranch repositoryBranch;

    @Transactional
    @Override
    public Branch saveit(BranchPojo branchPojo) throws Exception {
        Branch branch;
        if(branchPojo.getId()!=null) {
            branch = checkit(branchPojo.getId());
        }
        else {
            branch = new Branch();
        }
        branch.setName(branchPojo.getName());
        return repositoryBranch.save(branch);
    }

    @Override
    public List<Map<String, Object>> getAll() {
        return repositoryBranch.getAll();
    }

    @Override
    public void delete(Long id) {
        repositoryBranch.delete(new Branch(id));
    }

    @Override
    public Map<String, Object> getById(Long id) {
        return repositoryBranch.getById(id);
    }

    @Override
    public Branch checkit(Long id) throws Exception {
        Optional<Branch> branch = repositoryBranch.findById(id);
        if(!branch.isPresent())
            throw new Exception("Branch Not Found");
        return branch.get();
    }
}
