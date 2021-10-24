package com.varosa.audit.repository;


import com.varosa.audit.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    @Query(value = "select r.* from role r inner join user_role_group urg on urg.role_id = r.id inner join users u on u.id = urg.user_id where u.email=?1", nativeQuery = true)
    List<Role> getByUserEmail(String email);

    @Query(value = "Select a.id from role a where a.role_name = ?1",nativeQuery = true)
    Long getIdByRoleName(String name);


}
